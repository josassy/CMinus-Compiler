(FUNCTION  fact  []
  (BB 2
    (OPER 3 Func_Entry []  [])
  )
  (BB 3
    (OPER 4 Mov [(r 2)]  [(i 1)])
    (OPER 5 GT [(r 3)]  [(r 1)(r 2)])
    (OPER 6 BEQ []  [(r 3)(i 0)(bb 6)])
  )
  (BB 4
    (OPER 8 Mov [(r 4)]  [(i 1)])
    (OPER 9 Sub_I [(r 5)]  [(r 1)(r 4)])
    (OPER 7 Pass []  [(r 5)] [(PARAM_NUM 0)])
    (OPER 10 JSR []  [(s fact)] [(numParams 1)])
    (OPER 11 Mov [(r 6)]  [(m RetReg)])
    (OPER 12 Mul_I [(r 7)]  [(r 1)(r 6)])
    (OPER 13 Mov [(m RetReg)]  [(r 7)])
    (OPER 14 Jmp []  [(bb 1)])
  )
  (BB 5
  )
  (BB 1
    (OPER 1 Func_Exit []  [])
    (OPER 2 Return []  [(m RetReg)])
  )
  (BB 6
    (OPER 15 Mov [(r 8)]  [(i 1)])
    (OPER 16 Mov [(m RetReg)]  [(r 8)])
    (OPER 17 Jmp []  [(bb 1)])
    (OPER 18 Jmp []  [(bb 5)])
  )
)
(FUNCTION  main  []
  (BB 2
    (OPER 3 Func_Entry []  [])
  )
  (BB 3
    (OPER 4 JSR []  [(s read)] [(numParams 0)])
    (OPER 5 Mov [(r 2)]  [(m RetReg)])
    (OPER 6 Mov [(r 1)]  [(r 2)])
    (OPER 7 Mov [(r 3)]  [(i 0)])
    (OPER 8 GT [(r 4)]  [(r 1)(r 3)])
    (OPER 9 BEQ []  [(r 4)(i 0)(bb 5)])
  )
  (BB 4
    (OPER 11 Pass []  [(r 1)] [(PARAM_NUM 0)])
    (OPER 12 JSR []  [(s fact)] [(numParams 1)])
    (OPER 13 Mov [(r 5)]  [(m RetReg)])
    (OPER 10 Pass []  [(r 5)] [(PARAM_NUM 0)])
    (OPER 14 JSR []  [(s write)] [(numParams 1)])
    (OPER 15 Mov [(r 6)]  [(m RetReg)])
  )
  (BB 5
  )
  (BB 1
    (OPER 1 Func_Exit []  [])
    (OPER 2 Return []  [(m RetReg)])
  )
)
