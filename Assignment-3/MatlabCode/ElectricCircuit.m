E = 0.2;    
R = 512.0;
Isa = 0.0000006;
Isb = 0.0000012;
V1 = 0.0;
V2 = 0.0;
k = 0;
Vlist = [];
f1list = [];
f2list = [];
V = newRapCircuit(E,R,V1,V2,Isa,Isb,k);
Vlist = [Vlist, V];
f1 = V(1,1) - E + R * Isa *(exp((V(1,1) - V(2,1))/0.025) - 1.0);
f1list = [f1list, f1];
while (f1 > 10^(-6))
    k = k + 1;
    V = newRapCircuit(E,R,V(1,1),V(2,1),Isa,Isb,k);
    Vlist = [Vlist, V];
    f1 = V(1,1) - E + R * Isa *(exp((V(1,1) - V(2,1))/0.025) - 1.0);
    f2 = Isa * ((exp((V(1,1) - V(2,1)) / 0.025) - 1.0)) - Isb * (exp(V(2,1) / 0.025) - 1.0) ;
    f1list = [f1list, f1];
    f2list = [f2list, f1];
end
f1 = V(1,1) - E + R * Isa * (exp((V(1,1) - V(2,1)) / 0.025) - 1.0);
f2 = Isa * ((exp((V(1,1) - V(2,1)) / 0.025) - 1.0)) - Isb * (exp(V(2,1) / 0.025) - 1.0) ;
f1list = [f1list, f1];
f2list = [f2list, f1];
Vlist = Vlist';
f1list = f1list';
f2list = f2list';
klist = 1:k+2;
klist = klist';
figure 
plot(klist,f1list);
