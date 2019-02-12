% cell(State,Row,Col)
% control(2) means control blue player 
% witch goes from the left to the right side

oppRope(X,Y1,X,Y2):- cell(1,X,Y1),cell(1,X,Y2),cell(1,X,Y),Y1=Y-1,Y2=Y+1.
