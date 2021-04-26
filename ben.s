.text
	.align 4
.globl  test
test:
test_bb2:
test_bb3:
	movl	$0, %EAX
	movl	$1, %EAX
	movl	$0, %EAX
	movl	%EAX, %ESI
	movl	%ESI, %EDX
	cmpl	%ESI, %EDX
	jle	test_bb6
test_bb4:
	movl	$1, %EDI
	movl	%ESI, %EAX
	subl	%EDI, %EAX
	movl	%EAX, %ESI
test_bb5:
	cmpl	%ESI, %EDX
	jne	test_bb17
test_bb18:
	cmpl	%ESI, %EDX
	jne	test_bb17
test_bb19:
	jmp	test_bb18
test_bb21:
	cmpl	%ESI, %EDX
	jne	test_bb17
test_bb22:
	movl	$1, %EDI
	movl	%ESI, %EAX
	subl	%EDI, %EAX
	movl	%EAX, %EDX
	jmp	test_bb21
test_bb17:
	movl	$3, %EAX
test_bb1:
	ret
test_bb12:
	movl	$1, %EAX
	jmp	test_bb9
test_bb13:
	movl	$1, %EAX
	cmpl	%EAX, %ESI
	jne	test_bb6
test_bb14:
	movl	$2, %EDI
	movl	%ESI, %EAX
	addl	%EDI, %EAX
	movl	%EAX, %ESI
	jmp	test_bb13
test_bb6:
	movl	$2, %EAX
	movl	%EAX, %ESI
	jmp	test_bb5
test_bb7:
	movl	$2, %EAX
	cmpl	%EAX, %ESI
	jne	test_bb9
test_bb8:
	movl	$2, %EAX
	movl	%EAX, %ESI
	movl	$2, %EAX
	cmpl	%EAX, %ESI
	jne	test_bb12
	jmp	test_bb7
test_bb9:
