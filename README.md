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
                if (0 > 0) {
                    return;
                }
            }
            catch (InterruptedException ColonialObfuscator_垜懝悇守拯孆榩俫吟榇毆撰墈咭殟仭妺浾喯殕氷帉婀国惫横倾櫪嚭厑旆渒嬅夣噰唐榴欶楶枆嵽) {
                System.out.println(ColonialObfuscator_垜懝悇守拯孆榩俫吟榇毆撰墈咭殟仭妺浾喯殕氷帉婀国惫横倾櫪嚭厑旆渒嬅夣噰唐榴欶楶枆嵽);
            }
            ++Sender.a;
            System.out.println(ColonialObfuscator_忩噁峷捄坚侂憿槑亢墢敞囎孴嵯榣槤捺介尿寸厨巎洃佣塅榭宩动圍漑慯侫忠嶚湥櫛毶嘀命堅巹("夘够夅契") + Sender.a);
        } while (-4 < 0);
    }






Based on https://gitlab.com/nickfreeman/SimpleObfuscator and https://github.com/superblaubeere27/obfuscator
