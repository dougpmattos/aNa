package br.uff.midiacom.ana.util.enums;

public enum NCLScentType {

    ROSE("urn:mpeg:mpeg−v:01−SI−ScentCS−NS:rose"),
    ACACIA("urn:mpeg:mpeg−v:01−SI−ScentCS−NS:acacia"),
    CHRYSANTHEMUM("urn:mpeg:mpeg−v:01−SI−ScentCS−NS:chrysanthemum"),
    LILAC("urn:mpeg:mpeg−v:01−SI−ScentCS−NS:lilac"),
    MINT("urn:mpeg:mpeg−v:01−SI−ScentCS−NS:mint"),
    JASMINE("urn:mpeg:mpeg−v:01−SI−ScentCS−NS:jasmine"),
    PINE_TREE("urn:mpeg:mpeg−v:01−SI−ScentCS−NS:pine_tree"),
    ORANGE("urn:mpeg:mpeg−v:01−SI−ScentCS−NS:orange"),
    GRAPE("urn:mpeg:mpeg−v:01−SI−ScentCS−NS:grape");

    private String name;

    NCLScentType(String name) {
        this.name = name;
    }

    public static NCLScentType getEnumType(String name){
        for(NCLScentType opt : values()){
            if(name.equals(opt.name))
                return opt;
        }
        return null;
    }

    @Override
    public String toString() {
        return name;
    }

}
