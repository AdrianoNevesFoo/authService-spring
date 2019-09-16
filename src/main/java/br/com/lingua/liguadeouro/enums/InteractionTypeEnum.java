package br.com.lingua.liguadeouro.enums;

public enum InteractionTypeEnum {
    EVALUATE((short)1, "Evaluate"),
    UPDATE((short)5, "Update"),
    CREATE((short)10, "Create");


    private Short value;
    private String text;

    InteractionTypeEnum(Short value, String text) {
        this.value = value;
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public InteractionTypeEnum toEnum(String text) {

        for (InteractionTypeEnum type : InteractionTypeEnum.values()) {
            if(text.equals(type.getText()) || text.equals(type.toString())) {
                return  type;
            }
        }
        return  null;
    }
}