(FUNCTION  test  []
  (BB 2
    (OPER 3 Func_Entry []  [])
  )
  (BB 3
    (OPER 4 Mov [(r 3)]  [(i 0)])
    (OPER 5 Mov [(r 1)]  [(r 3)])
    (OPER 6 Mov [(r 4)]  [(i 1)])
    (OPER 7 Mov [(r 2)]  [(r 4)])
    (OPER 8 Mov [(r 5)]  [(i 0)])
    (OPER 9 Mov [(r 2)]  [(r 5)])
    (OPER 10 Mov [(r 1)]  [(r 2)])
    (OPER 11 GT [(r 6)]  [(r 1)(r 2)])
    (OPER 12 BEQ []  [(r 6)(i 0)(bb 6)])
  )
  (BB 4
    (OPER 13 Mov [(r 7)]  [(i 1)])
    (OPER 14 Sub_I [(r 8)]  [(r 2)(r 7)])
    (OPER 15 Mov [(r 2)]  [(r 8)])
  )
  (BB 5
    (OPER 41 EQ [(r 22)]  [(r 1)(r 2)])
    (OPER 42 BEQ []  [(r 22)(i 0)(bb 17)])
  )
  (BB 16
  )
  (BB 18
    (OPER 43 EQ [(r 23)]  [(r 1)(r 2)])
    (OPER 44 BEQ []  [(r 23)(i 0)(bb 20)])
  )
  (BB 19
    (OPER 51 Jmp []  [(bb 18)])
  )
  (BB 21
    (OPER 45 EQ [(r 24)]  [(r 1)(r 2)])
    (OPER 46 BEQ []  [(r 24)(i 0)(bb 23)])
  )
  (BB 22
    (OPER 47 Mov [(r 25)]  [(i 1)])
    (OPER 48 Sub_I [(r 26)]  [(r 2)(r 25)])
    (OPER 49 Mov [(r 1)]  [(r 26)])
    (OPER 50 Jmp []  [(bb 21)])
  )
  (BB 23
  )
  (BB 20
  )
  (BB 17
    (OPER 52 Mov [(r 27)]  [(i 3)])
    (OPER 53 Mov [(r 2)]  [(r 27)])
    (OPER 54 Jmp []  [(bb 1)])
  )
  (BB 1
    (OPER 1 Func_Exit []  [])
    (OPER 2 Return []  [(m RetReg)])
  )
  (BB 12
    (OPER 29 Mov [(r 17)]  [(i 1)])
    (OPER 30 Mov [(r 2)]  [(r 17)])
    (OPER 38 Jmp []  [(bb 11)])
  )
  (BB 13
    (OPER 31 Mov [(r 18)]  [(i 1)])
    (OPER 32 EQ [(r 19)]  [(r 2)(r 18)])
    (OPER 33 BEQ []  [(r 19)(i 0)(bb 15)])
  )
  (BB 14
    (OPER 34 Mov [(r 20)]  [(i 2)])
    (OPER 35 Add_I [(r 21)]  [(r 2)(r 20)])
    (OPER 36 Mov [(r 2)]  [(r 21)])
    (OPER 37 Jmp []  [(bb 13)])
  )
  (BB 15
  )
  (BB 6
    (OPER 16 Mov [(r 9)]  [(i 2)])
    (OPER 17 Mov [(r 2)]  [(r 9)])
    (OPER 40 Jmp []  [(bb 5)])
  )
  (BB 7
    (OPER 18 Mov [(r 10)]  [(i 2)])
    (OPER 19 EQ [(r 11)]  [(r 2)(r 10)])
    (OPER 20 BEQ []  [(r 11)(i 0)(bb 9)])
  )
  (BB 8
    (OPER 21 Mov [(r 12)]  [(i 2)])
    (OPER 22 Mov [(r 2)]  [(r 12)])
    (OPER 23 Mov [(r 13)]  [(i 2)])
    (OPER 24 EQ [(r 14)]  [(r 2)(r 13)])
    (OPER 25 BEQ []  [(r 14)(i 0)(bb 12)])
    (OPER 39 Jmp []  [(bb 7)])
  )
  (BB 10
    (OPER 26 Mov [(r 15)]  [(i 1)])
    (OPER 27 Add_I [(r 16)]  [(r 2)(r 15)])
    (OPER 28 Mov [(r 2)]  [(r 16)])
  )
  (BB 11
  )
  (BB 9
  )
)
