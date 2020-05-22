# ColonialObfuscator
Java Obfuscator in Beta using ASM Library

Before Obfuscation
    @Override
    public void run() {
        do {
            try {
                Thread.sleep(500L);
            }
            catch (InterruptedException e) {
                System.out.println(e);
            }
            System.out.println("int " + ++a);
        } while (true);
    }
After Obfuscation
    @Override
    public void run() {
        do {
            try {
                Thread.sleep(500L);
            }
            catch (InterruptedException ColonialObfuscator_2800725482253282661127458207272034022347284052755528347) {
                System.out.println(ColonialObfuscator_2800725482253282661127458207272034022347284052755528347);
            }
            System.out.println(Sender.2243723955267802685724238257242830622852281832190225217((char[])Sender.2411721019268352396522401255212806322301270322260221155((String)"\u5025\u5023\u5030\u5065")) + ++a);
        } while (true);
    }





Based on https://gitlab.com/nickfreeman/SimpleObfuscator and https://github.com/superblaubeere27/obfuscator
