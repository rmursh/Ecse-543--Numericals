function f = successiveSubstitution(x, tolerance)
SUo = (1*10^(-2))*(1*10^(-2))*4*pi*(10^-7);
La = 0.5*(10^(-2));
Rg = La/SUo;
i = 0;
while (abs(newtonRhapson(x,Rg)/newtonRhapson(0,Rg)) > tolerance)
	i = i + 1;
	x = fSubstitution(x); 
end
f = x;

function v = fSubstitution(flux)
B = flux/(1/(100)^2);
X = [0.0,0.2,0.4,0.6,0.8,1.0,1.1,1.2,1.3,1.4,1.5,1.6,1.7,1.8,1.9];
Y = [0.0, 14.7, 36.5, 71.7, 121.4, 197.4, 256.2,348.7,540.6,1062.8,2318.0,4781.9,8687.4,13924.3,22650.2];

xx = 0:.01:1.9;
P1 = piecelin(X,Y,xx);
v =  8000/(39.78873577e6 + 0.3*polyval(P1,B)/flux);