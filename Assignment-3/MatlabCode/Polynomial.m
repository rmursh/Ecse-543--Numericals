X = [0.0, 0.2, 0.4, 0.6, 0.8, 1.0];
Y = [0.0, 14.7, 36.5, 71.7, 121.4, 197.4];
xx = 0:.01:2.5;
P1 = Lagrange(X,Y);
figure;
plot(xx,Lagrange(X,Y,xx));

P2 = cubicHermiteInterpolation(X,Y,xx);
figure;
plot(xx,cubicHermiteInterpolation(X,Y,xx));

X= [0, 1.3, 1.4, 1.7, 1.8, 1.9];
Y = [0.0, 540.6, 1062.8, 8687.4, 13924.3, 22650.2];
P3 = Lagrange(X,Y);
figure;
plot(xx,Lagrange(X,Y,xx));

P4 = cubicHermiteInterpolation(X,Y,xx);
figure;
plot(xx,cubicHermiteInterpolation(X,Y,xx));

