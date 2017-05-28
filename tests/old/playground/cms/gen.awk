### input:
# int x
# float y
### output:
# private int   x;
# private float fY;
#
# public void setX(final int x)      { this.x  = x; }
# public void setFY(final float fY ) { this.fY = fY; }
#
# public int   getX()  { return this.x;  }
# public float getFY() { return this.fY; }
###
BEGIN {
  n       = 0;
  namelen = 0;
  typelen = 0;
}
{
  types[n] = $1;
  names[n] = $2;
  n++

  if (length($1) > typelen)
    typelen = length($1);
  if (length($2) > namelen)
    namelen = length($2);
}
function upper(str) {
  first = substr(str, 1, 1);
  rest  = substr(str, 2, 100);
  return toupper(first) rest;
}
END {
  namepad = substr("                              ", 1, namelen-1);
  typepad = substr("                              ", 1, typelen-1);

  print "//////////////////////////////////////////////////////////////////////////////"
  print "// private members                                                          //"
  print "//////////////////////////////////////////////////////////////////////////////"
  print ""

  for (i = 0; i < n; i++) {
    type = types[i]; tp = substr(typepad, length(type), 30);
    name = names[i]; np = substr(namepad, length(name), 30);
    printf("private %s%s %s;\n", type, tp, name);
  }

  print ""
  print "//////////////////////////////////////////////////////////////////////////////"
  print "// public setters                                                           //"
  print "//////////////////////////////////////////////////////////////////////////////"
  print ""

  for (i = 0; i < n; i++) {
    type = types[i]; tp = substr(typepad, length(type), 30);
    name = names[i]; np = substr(namepad, length(name), 30);
    printf("public void set%s%s (final %s %s)%s%s { this.%s%s = %s;%s }\n",
                  upper(name),np, type, name, tp,np, name,np, name,np);
  }

  print ""
  print "//////////////////////////////////////////////////////////////////////////////"
  print "// public getters                                                           //"
  print "//////////////////////////////////////////////////////////////////////////////"
  print ""

  for (i = 0; i < n; i++) {
    type = types[i]; tp = substr(typepad, length(type), 30);
    name = names[i]; np = substr(namepad, length(name), 30);
    printf("public %s%s get%s()%s { return this.%s;%s }\n",
               type,tp, upper(name),np,       name,np);
  }
}

