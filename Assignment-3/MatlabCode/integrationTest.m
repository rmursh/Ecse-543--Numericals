error = [];
N = [];
values = [];
for i = 1:20
   N = [N, i];
   values = [values, integration(0.0 , 1.0, i)]; 
   temp = abs(integration(0.0 , 1.0, i)-(-cos(1.0)-(-cos(0.0))));
   error = [error, temp];
end

plot(log(N), log(error));