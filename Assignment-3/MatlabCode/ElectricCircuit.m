E = 0.2;    
R = 512.0;
Isa = 0.0000006;
Isb = 0.0000012;
V1 = 0.0;
V2 = 0.0;
k = 0;

V = newRap(E,R,V1,V2,Isa,Isb,k);

f1 = V(1,1) - E + R * Isa *(exp((V(1,1) - V(2,1))/0.025) - 1.0);

% while (f1 > 0):
%     k += 1
%     V = newRap(E,R,V[0][0],V[1][0],Isa,Isb,k)
%     f1 = V[0][0] - E + R * Isa *(exp((V[0][0] - V[1][0])/0.025) - 1.0)
% f1 = V[0][0] - E + R * Isa * (math.exp((V[0][0] - V[1][0]) / 0.025) - 1.0)
% f2 = Isa * ((math.exp((V[0][0] - V[1][0]) / 0.025) - 1.0)) - Isb * (math.exp(V[1][0] / 0.025) - 1.0)  



    %V = matrixAddorSub(matTranspose(scalarMult(-1.0,matrixMult(invJ,f))),V,'a') 