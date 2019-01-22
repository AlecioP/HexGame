% util
maxCol(N):- N = #max{Col:cell(_,_,Col)}.
% general rule

pipeUntil(X,Y):- pipeUntil(X1,Y1),bridgeDouble(X,Y,X1,Y1).
pipeUntil(X,Y):- control(Color),pipeUntil(X1,Y1),adj(X,Y,X1,Y1),cell(Color,X,Y).

% control(2) means control blue player witch goes from the left to the right side

% basic step
pipeUntil(X,Y):-maxCol(N),N1=N+1,bridgeDouble(N1,N1,X,Y).
%CASE control(2)
pipeUntil(X,0):- control(2),cell(2,X,0).
%CASE control(1)
pipeUntil(0,Y):- control(1),cell(1,0,Y).

% last step
% in the aggregate the row value is for efficiency

potentialWin :- control(2),pipeUntil(_,N), maxCol(N).

% control(1) means control blue player witch goes from the top to the bottom side

% last step
% in the aggregate the column value is for efficiency

potentialWin :- control(1),pipeUntil(N,_),maxCol(N).


