.data
.comm	a,4,4

.text
	.align 4
.globl  addThem
addThem:
addThem_bb2:
	movl	%EDI, %EAX
	movl	%ESI, %EDI
addThem_bb3:
	addl	%EDI, %EAX
addThem_bb1:
	ret
.globl  putDigit
putDigit:
putDigit_bb2:
putDigit_bb3:
	movl	$48, %EAX
	addl	%EDI, %EAX
	movl	%EAX, %EDI
	call	putchar
putDigit_bb1:
	ret
.globl  printInt
printInt:
printInt_bb2:
	pushq	%R14
	pushq	%R15
	movl	%EDI, %R15D
printInt_bb3:
	movl	$0, %EAX
	movl	%EAX, %R14D
	movl	$10000, %EAX
	cmpl	%EAX, %R15D
	jl	printInt_bb6
printInt_bb4:
	movl	$45, %EAX
	movl	%EAX, %EDI
	call	putchar
	movl	$1, %EAX
	movl	%EAX, %EDI
	call	putDigit
printInt_bb1:
	popq	%R15
	popq	%R14
	ret
printInt_bb11:
	movl	$1, %EAX
	cmpl	%EAX, %R14D
	jne	printInt_bb10
printInt_bb12:
	movl	$0, %EAX
	movl	%EAX, %EDI
	call	putDigit
printInt_bb13:
	jmp	printInt_bb10
printInt_bb16:
	movl	$1, %EAX
	cmpl	%EAX, %R14D
	jne	printInt_bb15
printInt_bb17:
	movl	$0, %EAX
	movl	%EAX, %EDI
	call	putDigit
printInt_bb18:
	jmp	printInt_bb15
printInt_bb6:
	movl	$1000, %EAX
	cmpl	%EAX, %R15D
	jl	printInt_bb8
printInt_bb7:
	movl	$1000, %EDI
	movl	$0, %EDX
	movl	%R15D, %EAX
	idivl	%EDI, %EAX
	movl	%EAX, %R14D
	movl	%R14D, %EDI
	call	putDigit
	movl	$1000, %EDI
	movl	%R14D, %EAX
	imull	%EDI, %EAX
	movl	%EAX, %EDI
	movl	%R15D, %EAX
	subl	%EDI, %EAX
	movl	%EAX, %R15D
	movl	$1, %EAX
	movl	%EAX, %R14D
printInt_bb8:
	movl	$100, %EAX
	cmpl	%EAX, %R15D
	jl	printInt_bb11
printInt_bb9:
	movl	$100, %EDI
	movl	$0, %EDX
	movl	%R15D, %EAX
	idivl	%EDI, %EAX
	movl	%EAX, %R14D
	movl	%R14D, %EDI
	call	putDigit
	movl	$100, %EDI
	movl	%R14D, %EAX
	imull	%EDI, %EAX
	movl	%EAX, %EDI
	movl	%R15D, %EAX
	subl	%EDI, %EAX
	movl	%EAX, %R15D
	movl	$1, %EAX
	movl	%EAX, %R14D
printInt_bb10:
	movl	$10, %EAX
	cmpl	%EAX, %R15D
	jl	printInt_bb16
printInt_bb14:
	movl	$10, %EDI
	movl	$0, %EDX
	movl	%R15D, %EAX
	idivl	%EDI, %EAX
	movl	%EAX, %R14D
	movl	%R14D, %EDI
	call	putDigit
	movl	$10, %EDI
	movl	%R14D, %EAX
	imull	%EDI, %EAX
	movl	%EAX, %EDI
	movl	%R15D, %EAX
	subl	%EDI, %EAX
	movl	%EAX, %R15D
printInt_bb15:
	movl	%R15D, %EDI
	call	putDigit
	jmp	printInt_bb1
.globl  main
main:
main_bb2:
	pushq	%R14
	pushq	%R15
main_bb3:
	movl	$5, %EAX
	movl	%EAX, %R14D
	movl	%R14D, %ESI
	movl	$5, %EAX
	cmpl	%EAX, %ESI
	jne	main_bb6
main_bb4:
	movl	$3, %EAX
	movl	%EAX, a(%RIP)
main_bb5:
	movl	$0, %EAX
	movl	%EAX, %R15D
	movl	$1, %EAX
	movl	%EAX, %R14D
main_bb7:
	movl	$8, %EAX
	cmpl	%EAX, %R14D
	jg	main_bb9
main_bb8:
	movl	%R15D, %EAX
	addl	%R14D, %EAX
	movl	%EAX, %R15D
	movl	$1, %EDI
	movl	%R14D, %EAX
	addl	%EDI, %EAX
	movl	%EAX, %R14D
	jmp	main_bb7
main_bb9:
	movl	$3, %EDI
	movl	$0, %EDX
	movl	%R15D, %EAX
	idivl	%EDI, %EAX
	movl	%EAX, %EDX
	movl	$4, %EDI
	movl	%EDX, %EAX
	imull	%EDI, %EAX
	movl	%EAX, %EDI
	movl	%EDI, %R15D
	movl	a(%RIP), %EDI
	call	addThem
	movl	%EAX, %R14D
	movl	$56, %EAX
	movl	%EAX, %EDI
	call	putchar
	movl	$61, %EAX
	movl	%EAX, %EDI
	call	putchar
	movl	%R14D, %EAX
	addl	%R15D, %EAX
	movl	%EAX, %EDI
	call	putchar
	movl	$10, %EAX
	movl	%EAX, %EDI
	call	putchar
	movl	$0, %EAX
	movl	%EAX, %R14D
main_bb10:
	movl	$10, %EAX
	cmpl	%EAX, %R14D
	jge	main_bb12
main_bb11:
	movl	$48, %EAX
	addl	%R14D, %EAX
	movl	%EAX, %EDI
	call	putchar
	movl	$1, %EDI
	movl	%R14D, %EAX
	addl	%EDI, %EAX
	movl	%EAX, %R14D
	jmp	main_bb10
main_bb12:
	movl	$10, %EAX
	movl	%EAX, %EDI
	call	putchar
	movl	$67, %EAX
	movl	%EAX, %EDI
	call	putchar
	movl	$83, %EAX
	movl	%EAX, %EDI
	call	putchar
	movl	$3510, %EAX
	movl	%EAX, %EDI
	call	printInt
	movl	$10, %EAX
	movl	%EAX, %EDI
	call	putchar
	movl	$0, %EAX
	movl	%EAX, %ESI
	movl	$1, %EAX
	movl	%EAX, %R14D
	movl	$1, %EAX
	movl	%EAX, %R15D
	movl	$0, %EAX
	movl	%EAX, %EDX
	movl	$0, %EAX
	movl	$0, %EAX
	cmpl	%EAX, %ESI
	jne	main_bb15
main_bb13:
	movl	$0, %EAX
	cmpl	%EAX, %R14D
	jne	main_bb18
main_bb16:
	movl	$1, %EAX
	movl	%EAX, %R14D
main_bb14:
	movl	$10, %EAX
	cmpl	%EAX, %R14D
	jne	main_bb27
main_bb25:
	movl	$99, %EAX
	movl	%EAX, %EDI
	call	putchar
	movl	$0, %EAX
	movl	%EAX, %EDI
	call	putDigit
	movl	$0, %EAX
	movl	%EAX, %EDI
	call	putDigit
	movl	$108, %EAX
	movl	%EAX, %EDI
	call	putchar
main_bb26:
	movl	$10, %EAX
	movl	%EAX, %EDI
	call	putchar
	movl	$0, %EAX
main_bb1:
	popq	%R15
	popq	%R14
	ret
main_bb6:
	movl	$4, %EAX
	movl	%EAX, a(%RIP)
	jmp	main_bb5
main_bb24:
	movl	$3, %EAX
	movl	%EAX, %R14D
	jmp	main_bb14
main_bb21:
	movl	$0, %EAX
	cmpl	%EAX, %EDX
	jne	main_bb24
main_bb22:
	movl	$10, %EAX
	movl	%EAX, %R14D
main_bb23:
	jmp	main_bb14
main_bb18:
	movl	$0, %EAX
	cmpl	%EAX, %R15D
	jne	main_bb21
main_bb19:
	movl	$2, %EAX
	movl	%EAX, %R14D
main_bb20:
	jmp	main_bb14
main_bb15:
	movl	$0, %EAX
	movl	%EAX, %R14D
	jmp	main_bb14
main_bb27:
	movl	$98, %EAX
	movl	%EAX, %EDI
	call	putchar
	movl	$97, %EAX
	movl	%EAX, %EDI
	call	putchar
	movl	$100, %EAX
	movl	%EAX, %EDI
	call	putchar
	movl	$61, %EAX
	movl	%EAX, %EDI
	call	putchar
	movl	%R14D, %EDI
	call	printInt
	jmp	main_bb26
