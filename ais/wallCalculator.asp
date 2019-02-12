% cell(State,Row,Col)

oppWall(X1,Y,X2,Y):- cell(1,X1,Y), cell(1,X2,Y), cell(1,X,Y), X1=X-1, X2=X+1, X2>X1.

