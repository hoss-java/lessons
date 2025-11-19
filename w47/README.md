# Flowcharts

2. Calculate Total and Average Marks

```
START
  READ Subject1
  READ Subject2
  READ Subject3
  total = Subject1 + Subject2 + Subject3
  average = total / 3
  PRINT "Total =", total
  PRINT "Average =", average
END
```

```mermaid
flowchart TD
  Start([Start]) --> Input[/"Read marks"/]
  Input --> Sum["total = S1 + S2 + S3"]
  Sum --> Avg["average = total / 3"]
  Avg --> Output[/"Display total and average"/]
  Output --> End([End])
```
