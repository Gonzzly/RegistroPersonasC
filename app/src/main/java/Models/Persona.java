package Models;

public class Persona {
    private String nombre;
    private String apellidos;
    private  int edad;
    private String dNI;

    private float peso;
    private float altura;

    public Persona(String nombre, String apellidos, int edad, String dNI, float peso, float altura) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.edad = edad;
        this.dNI = dNI;
        this.peso = peso;
        this.altura = altura;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getDNI() {
        return dNI;
    }

    public void setDNI(String dNI) {
        this.dNI = dNI;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public float getAltura() {
        return altura;
    }

    public void setAltura(float altura) {
        this.altura = altura;
    }
    public int CalcularIMC(){
        float pesoIdeal= peso/(float) Math.pow(altura,2);
        if (pesoIdeal<20)
            return -1;
        else if(pesoIdeal>=20 && pesoIdeal<=25)
            return 0;
        else if (pesoIdeal>25)
            return 1;
        else
        return 50;
    }

    public boolean mayorEdad(){
        if (edad>=18){
            return true;
        }else
            return false;
    }
    public boolean verificarDNI(){
        if (dNI.length() ==8)
            return true;
        else
        return false;
    }

    @Override
    public String toString() {
        String tipoPeso="";
        switch (CalcularIMC()){
            case -1:
                tipoPeso="debajo de ideal";
                break;
            case 0:
                tipoPeso ="ideal";
                break;
            case 1:
                tipoPeso ="sobre ideal";
                break;
        }
        return nombre+" "+apellidos+
                " tiene tipo de peso "+tipoPeso+
                " y es "+(mayorEdad()?" mayor de edad":" menor de edad");
    }
}
