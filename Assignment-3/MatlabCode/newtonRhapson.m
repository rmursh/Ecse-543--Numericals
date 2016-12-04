function f = newtonRhapson(flux, Rg)

B = flux/(1/(100)^2);
X = [0.0,0.2,0.4,0.6,0.8,1.0,1.1,1.2,1.3,1.4,1.5,1.6,1.7,1.8,1.9];
Y = [0.0, 14.7, 36.5, 71.7, 121.4, 197.4, 256.2,348.7,540.6,1062.8,2318.0,4781.9,8687.4,13924.3,22650.2];

xx = 0:.01:1.9;
P1 = piecelin(X,Y,xx);
% index = find(xx==flux);
% P1(1);
% Y_point = P1(index);
%plot(P1,xx);

f = Rg*flux + 0.3*polyval(P1,B) - 8000; 
%f = Rg*flux + 0.3*polyval(P1,flux) - 8000; 
