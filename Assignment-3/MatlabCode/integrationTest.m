error = [];
N = [];
x = 0:0.01:1;
%plot(log(0.2*abs(sin(x))));
fun = @(x) log(0.2*abs(sin(x)));
q = integral(fun,0,1);
for i = 10:10:200
   N = [N, i];
   %temp = abs(integration(0.0 , 1.0, i)-(-cos(1.0)-(-cos(0.0))));
   %temp = abs(integration(0.0 , 1.0, i)-(-1));
   temp = abs(integration(0.0 , 1.0, i)-q);
   error = [error, temp];
end



