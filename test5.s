.text
	.align 4
.globl  addThem
addThem:
addThem_bb2:
addThem_bb3:
	addl	%EDI, %EAX
addThem_bb1:
	ret
.globl  main
main:
main_bb2:
	pushq	%R15
main_bb3:
	movl	$5, %EAX
	movl	%EAX, %ESI
	movl	$5, %EAX
	cmpl	%EAX, %ESI
	jne	main_bb6
main_bb4:
	movl	$3, %EAX
	movl	%EAX, %ECX
main_bb5:
	movl	$0, %EAX
	movl	%EAX, %R15D
	movl	$1, %EAX
	movl	%EAX, %EDX
main_bb7:
	movl	$8, %EAX
	cmpl	%EAX, %EDX
	jg	main_bb9
main_bb8:
	movl	%R15D, %EAX
	addl	%EDX, %EAX
	movl	%EAX, %R15D
	movl	$1, %EDI
	movl	%EDX, %EAX
	addl	%EDI, %EAX
	movl	%EAX, %EDX
	jmp	main_bb7
main_bb9:
	movl	$3, %EDI
	movl	$0, %EDX
	movl	%R15D, %EAX
	idivl	%EDI, %EAX
	movl	$4, %EDI
	imull	%EDI, %EAX
	movl	%EAX, %EDI
	movl	%EDI, %R15D
	movl	%ECX, %EDI
	call	addThem
	addl	%R15D, %EAX
	movl	%EAX, %EDI
	call	putchar
	movl	$10, %EAX
	movl	%EAX, %EDI
	call	putchar
	movl	$0, %EAX
main_bb1:
	popq	%R15
	ret
main_bb6:
	movl	$4, %EAX
	movl	%EAX, %ECX
	jmp	main_bb5
