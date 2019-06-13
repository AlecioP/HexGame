% HEX CELLS ADJACENCY%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
adj(X,Y,X,Y1):- cell(X,Y),cell(X,Y1),Y1=Y+1.			%
adj(X,Y,X,Y1):- cell(X,Y),cell(X,Y1),Y1=Y-1.			%
adj(X,Y,X1,Y):- cell(X,Y),cell(X1,Y),X1=X+1.			%
adj(X,Y,X1,Y):- cell(X,Y),cell(X1,Y),X1=X-1.			%
adj(X,Y,X1,Y1):-cell(X,Y),cell(X1,Y1),X1=X+1,Y1=Y+1.	%
adj(X,Y,X1,Y1):-cell(X,Y),cell(X1,Y1),X1=X-1,Y1=Y-1.	%
adj(X,Y,X1,Y1):-adj(X1,Y1,X,Y).							%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
cell(X,Y):-cell(S,X,Y).