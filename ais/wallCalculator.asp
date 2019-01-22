% cell(State,Row,Col)

oppWall(X1,Y,X2,Y):-control(2),cell(1,X1,Y),cell(1,X2,Y),cell(1,X,Y),X1=X-1,X2=X+1.

oppWall(X,Y1,X,Y1):-control(1),cell(2,X,Y1),cell(2,X,Y2),cell(2,X,Y),Y1=Y-1,Y2=Y+1.