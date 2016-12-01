SUo = (1*10^(-2))*(1*10^(-2))*4*pi*(10^-7);
Nturn = 800;
I = 10;
M = Nturn*I;
La = 0.5*(10^(-2));
Lc = 30*(10^(-2));
Rg = La/SUo;
tolerance = 0.000001; 
i = 0;
x = 0;

while (abs(newtonRhapson(x,Rg)/newtonRhapson(0,Rg)) > tolerance)
    i = i + 1;
    x = x - newtonRhapson(x, Rg)/newtonRhapsonDer(x,Rg);  
end

