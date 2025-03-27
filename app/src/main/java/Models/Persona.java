package Models;

import java.io.Serializable;

public class Persona implements Serializable {
    private String nombre;
    private String apellidos;
    private String sexo;
    private String ciudad;
    private int edad;
    private String dni;
    private double peso;
    private double altura;
    private byte[] foto;

    public Persona(String nombre, String apellidos, String sexo, String ciudad, int edad, String dni, double peso, double altura, byte[] foto) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.sexo = sexo;
        this.ciudad = ciudad;
        this.edad = edad;
        this.dni = dni;
        this.peso = peso;
        this.altura = altura;
        this.foto = foto;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public double getPeso() { return peso; }
    public void setPeso(double peso) { this.peso = peso; }

    public double getAltura() { return altura; }
    public void setAltura(double altura) { this.altura = altura; }

    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }

    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }

    public byte[] getFoto() { return foto; }

    public String getNombreCompleto() { return apellidos + " " + nombre; }

    public String getTipoPeso() {
        String[] tipoPeso = {"Debajo de Ideal", "Ideal", "Sobre Ideal"};
        return tipoPeso[CalcularIMC() + 1];
    }

    public String getTipoPersona() {
        return mayorEdad() ? "Mayor de edad" : "Menor de edad";
    }

    public int CalcularIMC() {
        double imc = peso / Math.pow(altura, 2);
        if (imc < 20) return -1;
        if (imc >= 20 && imc <= 25) return 0;
        return 1;
    }

    public boolean mayorEdad() {
        return edad >= 18;
    }

    public boolean verificarDNI() {
        return dni.length() == 8;
    }

    @Override
    public String toString() {
        String tipoPeso = "";
        switch (CalcularIMC()) {
            case -1:
                tipoPeso = "debajo de ideal";
                break;
            case 0:
                tipoPeso = "ideal";
                break;
            case 1:
                tipoPeso = "sobre ideal";
                break;
        }
        return nombre + " " + apellidos +
                " tiene tipo de peso " + tipoPeso +
                " y es " + (mayorEdad() ? "mayor de edad" : "menor de edad");
    }
}
