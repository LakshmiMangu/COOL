class Main inherits IO{
	n:Int;
	main():Object{
		{
			n <- in_int();
			-- d(n);
		}
	};

	d ( k : Int) : Object{
		{
			k <- k-1;
			out_int(k);
		}
	};
};

class A inherits B{

};

class B inherits C{

};

class C inherits A{

};