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
                if (0 != 0) {
                    return;
                }
            }
            catch (InterruptedException e) {
                System.out.println(e);
            }
            System.out.println(Sender.ColonialObfuscator_2808627728259872418421589262942461925720232642239823421((char[])Sender.ColonialObfuscator_2391924132244302371628207238292291222328255902813522945((String)"\u4ef7\u4ef1\u4ee4\u4eb1")) + ++a);
        } while (2 <= 2);
    }






Based on https://gitlab.com/nickfreeman/SimpleObfuscator and https://github.com/superblaubeere27/obfuscator
