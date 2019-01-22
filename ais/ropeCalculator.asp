% cell(State,Row,Col)
% control(2) means control blue player 
% witch goes from the left to the right side

oppRope(X,Y1,X,Y2):- control(2),cell(1,X,Y1),cell(1,X,Y2),cell(1,X,Y),Y1=Y-1,Y2=Y+1.

oppRope(X1,Y,X2,Y):- control(1),cell(2,X1,Y),cell(2,X2,Y),cell(2,X,Y),X1=X-1,X2=X+1.


