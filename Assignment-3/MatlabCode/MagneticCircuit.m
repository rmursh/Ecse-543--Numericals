SUo = (1*10^(-2))*(1*10^(-2))*4*pi*(10^-7);
Nturn = 800;
I = 10;
M = Nturn*I;
La = 0.5*(10^(-2));
Lc = 30*(10^(-2));
Rg = La/SUo;
tolerance = 0.000001; 
iterations = 0;
x = 0;
xlist = [];
while (abs(newtonRhapson(x,Rg)/newtonRhapson(0,Rg)) > tolerance)
    iterations = iterations + 1;
    x = x - newtonRhapson(x, Rg)/newtonRhapsonDer(x,Rg);  
    xlist = [xlist, x];
end
xlist = xlist';
ilist = 1 : iterations;
ilist = ilist';
%plot(ilist, xlist);