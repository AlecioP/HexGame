empty(R,C):- cell(0,R,C).
response(R,C)| notResp(R,C):-empty(R,C).

:- not #count{R,C:response(R,C)} = 1.
