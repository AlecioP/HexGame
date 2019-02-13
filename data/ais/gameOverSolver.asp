%Basic step

pipe("blue",R,C):- cell(2,R,C), C=0.

pipe("red",R,C):- cell(1,R,C), R=0.

%Recursive step

pipe(ColorTxt,R1,C1):-pipe(ColorTxt,R,C), cell(Color,R1,C1), adj(R,C,R1,C1),Color=2,ColorTxt="blue".
pipe(ColorTxt,R1,C1):-pipe(ColorTxt,R,C), cell(Color,R1,C1), adj(R,C,R1,C1),Color=1,ColorTxt="red".
%Final step

win(0):- pipe("blue",R,C), boardDim(N), C=N.

win(1):- pipe("red",R,C), boardDim(N), R=N.

%Support

boardDim(N):- N = #max{C:cell(_,_,C)}.

cell(R,C):- cell(_,R,C).

% HEX CELLS ADJACENCY%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
adj(X,Y,X,Y1):- cell(X,Y),cell(X,Y1),Y1=Y+1.			%
adj(X,Y,X,Y1):- cell(X,Y),cell(X,Y1),Y1=Y-1.			%
adj(X,Y,X1,Y):- cell(X,Y),cell(X1,Y),X1=X+1.			%
adj(X,Y,X1,Y):- cell(X,Y),cell(X1,Y),X1=X-1.			%
adj(X,Y,X1,Y1):-cell(X,Y),cell(X1,Y1),X1=X+1,Y1=Y+1.	%
adj(X,Y,X1,Y1):-cell(X,Y),cell(X1,Y1),X1=X-1,Y1=Y-1.	%
adj(X,Y,X1,Y1):-adj(X1,Y1,X,Y).							%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
