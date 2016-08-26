#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <unistd.h>
#include <sys/wait.h>

#define MAX_BUFFER 1024                        // max line buffer
#define MAX_ARGS 64                            // max # args
#define SEPARATORS " \t\n"                     // token sparators

sig_atomic_t child_exit_status;
int check = 0;
//struct for pids
typedef struct{
int pidI,done;
struct pidH * next;
}pidH;

// to clean up and finished processes
void clean_up_child_process(int signal_number){

int waiting;
wait(&waiting);
child_exit_status = waiting;

}
//to print and remove list
void print(pidH*head){
pid_t pid;
int status;
pidH * temp = head;
printf("PID:");
while(head != NULL){
pid = head->pidI;
if(waitpid(pid,&status,WNOHANG) == 0){
printf(" %d",pid);
}
temp = head ;
head = head->next;
free(temp);
}
printf("\n");
}

//remove all pids gathered
pidH * removeALL(pidH * head){
    pid_t pid;
    pidH * temp = head;
    int k = 0;
    int status;

    while(head != NULL){
    int i = head->pidI;
    pid = (pid_t)i;

    //checking for running processes
    if(waitpid(pid,&status,WNOHANG) == 0){
    k++;
    }

    head = head->next;
    }

    head = temp ;
    printf("Murdering %d processes: ",k);
    while (head != NULL){
        if(waitpid(head->pidI,&status,WNOHANG) == 0){
        kill(head->pidI,SIGTERM);
        printf("%d ",head->pidI);
        }
        temp = head;
        head = head->next;
        free(temp);
    }
    printf("\n");

    return head;

}

//create a new process
int spawn(char*pro,char**argv){
int status;
pid_t child_pid;
child_pid = fork();
if(child_pid !=0){
return child_pid;
}else{
execvp(pro,argv);
printf("error occurred or path not found\n");
exit(0);
}


}
//remove a pid that my be in process
pidH* removePID(pidH * head , int pid){
    pidH *start = head,*temp = head,*temp2 = head;

    if(head != NULL){
    if(head->pidI == pid){
    head = head->next;
    free(temp);
    return head;
    }else{
    while(head->pidI != pid && head->next != NULL){
    temp2 = head ;
    head = head->next;
    }
    if(head->pidI == pid){
    temp2->next = head->next;
    temp2 = head;
    free(temp2);
    return start;
    }
    }
    }

return start;
}
// add pid to list
pidH* add(pidH* head,int pid){
    pidH * temp = head ,*temp2;

    if(head == NULL){
    head = malloc(sizeof(pidH));
    head->pidI = pid;
    head->next = NULL;
    return head;
    }else{
    while(head->next != NULL){
    head = head->next;
    }
    temp2 = head;
    head = head->next;
    head = malloc(sizeof(pidH));
    head->pidI = pid;
    head->next = NULL;
    temp2->next = head;
    return temp;

    }
}


int main (int argc, char ** argv)
{
    char buf[MAX_BUFFER];
    struct sigaction sigchild_action;                     // line buffer
    char * args[MAX_ARGS];                     // pointers to arg strings
    char ** arg;                               // working pointer thru args
    char * prompt = "# " ;
    int child_wait;
    pidH *head = NULL;
    pid_t pid;
    pid_t child_pid;                // shell prompt

/* keep reading input until "quit" command or eof of redirected input */

fputs (prompt, stdout);
    while (!feof(stdin)) {







/* get command line from input */


        if (fgets (buf, MAX_BUFFER, stdin )) { // read a line

/* tokenize the input into args array */

            arg = args;
            *arg++ = strtok(buf,SEPARATORS);   // tokenize input
            while ((*arg++ = strtok(NULL,SEPARATORS)));
                                               // last entry will be NULL

            if (args[0]) {                     // if there's anything there

/* check for internal/external command */

            check = 0 ;

                if (!strcmp(args[0],"run")) { // "run" command
                    arg = args;
                     *arg++;
                pid = spawn(arg[0],arg);

                waitpid(pid,&child_wait,WUNTRACED);
                if(WIFEXITED(child_wait)){
                printf("the child code exited normally, with exit code %d\n",WIFEXITED(child_wait));
                }else{
                printf("The child process exited abnormally\n");
                }
                check++;
                }

                if (!strcmp(args[0],"background")) { // "Background" command

                    memset(&sigchild_action,0,sizeof(sigchild_action));
                    sigchild_action.sa_handler = &clean_up_child_process;
                    sigaction(SIGCHLD,&sigchild_action,NULL);
                    arg = args;
                     *arg++;
                    pid = child_pid = fork();
                    if(pid == 0){
                    execvp(arg[0],arg);
                    printf("error occurred or path not found\n# ");
                    exit(0);
                    }

                    head = add(head,(int)pid);
                    printf("PID %d \n",pid);
                    check++;

                }
                pidH * headTemp = NULL;
                if (!strcmp(args[0],"repeat")) { // "repeat" command
                    int r = atoi(args[1]);
                    int c = 0;






                    for( r ; r > 0 ; r--){
                    c++;
                    memset(&sigchild_action,0,sizeof(sigchild_action));
                    sigchild_action.sa_handler = &clean_up_child_process;
                    sigaction(SIGCHLD,&sigchild_action,NULL);
                    arg = args;
                    *arg++;
                    *arg++;
                     pid = child_pid = fork();
                    if(pid == 0){
                    execvp(arg[0],arg);
                    printf("error occurred or path not found\n# ");
                    exit(0);
                    }

                    head = add(head,(int)pid);
                    headTemp = add(headTemp,(int)pid);

                    }

                      print(headTemp);
                      if(c == 0){
                      printf("no number entered or zero entered.\n");
                      }


                    check++;
                }

                if (!strcmp(args[0],"murder")) { // "murder" command
                    int k;
                    if(args[1]==NULL){
                    printf("no PID\n");
                    check++;
                    }else{
                    k = atoi(args[1]);
                    pid = (pid_t)k;
                    kill(pid,SIGKILL);
                    head = removePID(head,pid);
                    check++;
                    }

                }

                if (!strcmp(args[0],"murderall")) { // "murderall" command
                    head = removeALL(head);
                    check++;
                }

                if (!strcmp(args[0],"clear")) { // "clear" command
                    check++;
                    fputs (prompt, stdout);
                    system("clear");
                    continue;

                }

                if (!strcmp(args[0],"quit"))   // "quit" command
                    break;                     // break out of 'while' loop

/* else pass command onto OS (or in this instance, print them out) */

                arg = args;
                while (*arg) fprintf(stdout,"%s ",*arg++);
                fputs ("\n", stdout);
                if(check == 0)
                printf("command not found\n");
                fputs (prompt, stdout);      // write prompt


            }
        }
    }
    return 0;
}
