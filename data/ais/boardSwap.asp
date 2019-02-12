%to avoid problems with control(2)
cell(State,Row,Column):-control(2), auxCell(State,Row,Column).

cell(2,Column,Row):- control(1), auxCell(State,Row,Column), State=1.
cell(1,Column,Row):- control(1), auxCell(State,Row,Column), State=2.
cell(0,Column,Row):- control(1), auxCell(State,Row,Column), State=0.
