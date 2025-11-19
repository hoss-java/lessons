# Flowcharts

2. Calculate Total and Average Marks

```pseudocode
START
  READ A, B , C
  total = A + B + C
  average = total / 3
  PRINT "Total =", total
  PRINT "Average =", average
END
```

```mermaid
flowchart TD
  Start([Start]) --> Input[/"Read A, B, C"/]
  Input --> Sum["total = A + B + C"]
  Sum --> Avg["average = total / 3"]
  Avg --> Output[/"Display total and average"/]
  Output --> End([End])
```
---
3. Display Multiplication Table
```pseudocode
START
  READ n
  i = 1
  WHILE i <= 10 DO
    product = n * i
    PRINT n, "x", i, "=", product
    i = i + 1
  END WHILE
END
```
```mermaid
flowchart TD
  Start([Start]) --> Input[/"Read n"/]
  Input --> Init["i = 1"]
  Init --> Check{ i <= 10 ? }
  Check -- Yes --> Calc["product = n * i"]
  Calc --> Output[/"Display n x i = product"/]
  Output --> Increment["i = i + 1"]
  Increment --> Check
  Check -- No --> End([End])
```
---
4. Positive, Negative, or Zero Check
```pseudocode
START
  READ n
  IF n > 0 THEN
    PRINT "Positive"
  ELSE IF n < 0 THEN
    PRINT "Negative"
  ELSE
    PRINT "Zero"
  END IF
END
```
```mermaid
flowchart TD
  Start([Start]) --> Input[/"Read n"/]
  Input --> CheckPos{ n > 0 ? }
  CheckPos -- Yes --> Pos[/"Display 'Positive'"/]
  CheckPos -- No --> CheckNeg{ n < 0 ? }
  CheckNeg -- Yes --> Neg[/"Display 'Negative'"/]
  CheckNeg -- No --> Zero[/"Display 'Zero'"/]
  Pos --> End([End])
  Neg --> End
  Zero --> End
```
---
5. Simple Interest Calculator
```pseudocode
START
  READ P
  READ R
  READ T
  SI = (P * R * T) / 100
  PRINT "Simple Interest =", SI
END
```
```mermaid
flowchart TD
  Start([Start]) --> Input[/"Read P, R, T"/]
  Input --> Calc["SI = (P * R * T) / 100"]
  Calc --> Output[/"Display SI"/]
  Output --> End([End])
```
---
6. Average Temperature Calculation
```pseudocode
START
  total = 0
  i = 1
  WHILE i <= 7 DO
    READ temp
    total = total + temp
    i = i + 1
  END WHILE
  average = total / 7
  PRINT "Average =", average
END
```
```mermaid
flowchart TD
  Start([Start]) --> Init["total = 0 \ni = 1"]
  Init --> Check{ i <= 7 ? }
  Check -- Yes --> Input[/"Read temp"/]
  Input --> Accum["total = total + temp"]
  Accum --> Increment["i = i + 1"]
  Increment --> Check
  Check -- No --> Compute["average = total / 7"]
  Compute --> Output[/"Display average"/]
  Output --> End([End])
```
---
7. Calculate Area of a Rectangle
```pseudocode
START
  READ L
  READ W
  area = L * W
  PRINT "Area =", area
END
```
```mermaid
flowchart TD
  Start([Start]) --> Input[/"Read Length L and Width W"/]
  Input --> Calc["area = L * W"]
  Calc --> Output[/"Display area"/]
  Output --> End([End])
```
---
8. Determine Pass or Fail
```pseudocode
START
  READ avg
  IF avg >= 50 THEN
    PRINT "Pass"
  ELSE
    PRINT "Fail"
  END IF
END
```
```mermaid
flowchart TD
  Start([Start]) --> Input[/"Read average marks (avg)"/]
  Input --> Check{ avg >= 50 ? }
  Check -- Yes --> Pass[/"Display 'Pass'"/]
  Check -- No --> Fail[/"Display 'Fail'"/]
  Pass --> End([End])
  Fail --> End
```
---
9. Calculate Factorial of a Number
```pseudocode
START
  READ n
  fact = 1
  i = 1
  WHILE i <= n DO
    fact = fact * i
    i = i + 1
  END WHILE
  PRINT "Factorial =", fact
END
```
```mermaid
flowchart TD
  Start([Start]) --> Input[/"Read n (n ≥ 0)"/]
  Input --> Init["fact = 1\ni = 1"]
  Init --> Check{ i <= n ? }
  Check -- Yes --> Multiply["fact = fact * i"]
  Multiply --> Increment["i = i + 1"]
  Increment --> Check
  Check -- No --> Output[/"Display fact"/]
  Output --> End([End])
```
---
10. Calculate Discount on Purchase
```pseudocode
START
  READ purchase_amount
  IF purchase_amount > 1000 THEN
    discount = purchase_amount * 0.10
    final_amount = purchase_amount - discount
  ELSE
    discount = 0
    final_amount = purchase_amount
  END IF
  PRINT "Discount =", discount
  PRINT "Final Amount =", final_amount
END
```
```mermaid
flowchart TD
  Start([Start]) --> Input[/"Read purchase_amount"/]
  Input --> Check{ purchase_amount > 1000 ? }
  Check -- Yes --> CalcDisc["discount = purchase_amount * 0.10"]
  CalcDisc --> CalcFinal["final_amount = purchase_amount - discount"]
  CalcFinal --> Output[/"Display discount and final_amount"/]
  Check -- No --> NoDisc["discount = 0\nfinal_amount = purchase_amount"]
  NoDisc --> Output
  Output --> End([End])
```
