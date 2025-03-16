org 100h
.MODEL SMALL
.STACK 100h 

.DATA

 num dw 0
 digit_count dw 0

.CODE

MAIN PROC

 mov ax, @data 
 mov ds, ax
 
read: 
 MOV AH, 01
 INT 21H
 CMP AL, 0DH
 je store_in_stack
 
 SUB AL, '0'
 MOV AH, 0
 ;pushing the current number into stack
 PUSH AX
 ;multiplying previous number by 10 and store it in AX
 MOV AL, 10
 MUL NUM
 ;popping current number from stack
 POP BX
 ;adding current number after the previous have been mutliplied by 10 in ax
 ADD AX,BX  
 ;storing processed number back in number
 MOV NUM, AX     
 INC digit_count 
 JMP read
 
 store_in_stack:
 
 mov ax, num
 mov bx, 10
 mov dx, 0
 DIV bx
 
 PUSH DX
 mov num, ax
  
 CMP num,0
 JNE store_in_stack

mov ah, 02
mov dl, 10
int 21h
mov dl ,13
int 21h
 
 
MOV CX, digit_count 
 pop_from_stack:
 
 MOV AH, 02
 pop dx
 mov dh, 0
 ADD Dl, '0'
 int 21h 
 loop pop_from_stack
 

 ;TO PRINT THE NUMBER, YOU WILL HAVE TO USE A DIVIDE BY 10 TECHNIQUE. 
 
;IMPORTANT NOTES:
; 1- MAKE SURE TO ZERO AH BEFORE PUSHING TO STACK
; 2- MAKE SURE DEFINTE THE NUMBER AS WORD AND NOT BYTE
; 3- MAKE SURE TO POP INTO BX, AND NOT BL  
   MOV AH, 4CH
   INT 21H 
    
    
MAIN ENDP
END MAIN