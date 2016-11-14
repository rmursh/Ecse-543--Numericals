function [x] = solveSystem(A,h,b);
    n = length(A);
    L = zeros(n);
 %Calculate Y from b
    y = zeros(n,1);

    for i = 1:n
        s = i-h;
        if (i <= h)
            s=1;
        end
        y(i,1) = (b(i) - sum(L(i, s:i-1).*(y(s:i-1)')))/L(i,i);
    end

    %Calculate X from Y
    x = zeros(n,1);
    U = L';
    for i = n:-1:1
         e = i+h;
        if (i+h >=n)
            e=n;
        end
        x(i,1) = (y(i) - sum(U(i, i+1:e).*(x(i+1:e)')))/U(i,i);
    end

end