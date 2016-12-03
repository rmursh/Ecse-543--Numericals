function f = newtonRhapsonDer(flux, Rg)

X = [0.0,0.2,0.4,0.6,0.8,1.0,1.1,1.2,1.3,1.4,1.5,1.6,1.7,1.8,1.9];
Y = [0.0, 14.7, 36.5, 71.7, 121.4, 197.4, 256.2,348.7,540.6,1062.8,2318.0,4781.9,8687.4,13924.3,22650.2];
xx = 0:.01:1.9;
P1 = piecelin(X,Y,xx);
Ydiff = gradient(P1);

%plot(Ydiff,xx);
%plot(xx,P1,xx(index),Y_point,'o');
f = Rg + (0.3*polyval(Ydiff, flux)/(1/100^2));