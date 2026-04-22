package com.politecnicomalaga.clinicadentista.dataservice;

import android.os.Handler;
import android.os.Looper;

import com.politecnicomalaga.clinicadentista.controller.Controlador;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BBDDAccess {
    private final Controlador c;
    private static final String IP = "192.168.56.49:8080"; //IP de aula 06

    public BBDDAccess(Controlador c) {
        this.c = c;
    }

    public void insertarCita(int idPaciente, int idDoctor, String fecha, String motivo) {
        //Cliente HTTP
        String URL = "http://" + IP + "/insertar";

        //Añadimos parámetros al GET
        URL += "?idPaciente=" + idPaciente + "&idDoctor=" + idDoctor + "&fecha=" + fecha + "&motivo=" + motivo;
        OkHttpClient clientHTTP = new OkHttpClient();

        //Petición a realizar al cliente HTTP. Patrón de diseño "Builder". Es decir "poco a poco"
        Request request = new Request.Builder()
                .url(URL)  //dirección web
                .get()     //función http a utilizar, puede ser post, put,...
                .addHeader("accept", "application/json")  //Qué formato queremos
                .build();   //a construir la request!!

        //realizamos la llamada al server, pero en otro thread (con enqueue)
        //Primero, una llamada al server
        Call llamada = clientHTTP.newCall(request);

        //Ponemos la llamada en cola para que salga por la tarjeta de red que tengamos en el móvil activa, y creamos un
        //objeto anónimo CallBack como repsuesta

        llamada.enqueue(new Callback() {
            public void onResponse(Call call, Response respuestaServer)
                    throws IOException {
                //Cogemos el cuerpo de la respuesta
                String respuesta = respuestaServer.body().string();

                // Create a handler that associated with Looper of the main thread
                //Un manejador es un "bucle" en esencia que ejecuta uno a uno todos los procesos de la App
                Handler manejador = new Handler(Looper.getMainLooper()); //pedimos el principal de la app
                // Send a task to the MessageQueue of the main thread
                manejador.post(new Runnable() {
                    @Override
                    public void run() {
                        // Code will be executed on the main thread
                        //Este es código que realmente se ejecuta cuando se recibe la respuesta.
                        c.setData(respuesta, false);
                    }
                });
            }

            public void onFailure(Call call, IOException e) {
                //Cuidado, puede que haya alguna vez un fallo en la respuesta. Entonces entra por aquí
                String respuesta = e.getMessage(); //Fijaros que nos pasan la excepción con el problema.
                //Lo típico es "sacar" el string mensaje de la exceptión y mandarla al sistema principal para
                //que se vea que ha pasado
                Handler manejador = new Handler(Looper.getMainLooper());

                // Send a task to the MessageQueue of the main thread
                manejador.post(new Runnable() {
                    @Override
                    public void run() {
                        // Code will be executed on the main thread

                        c.setData(respuesta, true);

                    }
                });
            }
        });
    }

    //Fijaros que ahora listarTodos devuelve... ¡void!. Por que los datos no los puede
    //conseguir "directamente". Lo que hacen estos métodos es pedir los datos, y poner un hilo
    // a funcionar como encargado de "escuchar" la posible respuesta
    public void listarTodos() {
        String URL = "http://" + IP + "/listar";

        OkHttpClient clientHTTP = new OkHttpClient();

        Request request = new Request.Builder()
                .url(URL)  //dirección web
                .get()     //función http a utilizar, puede ser post, put,...
                .addHeader("accept", "application/json")  //Qué formato queremos
                .build();   //a construir la request!!

        Call llamada = clientHTTP.newCall(request);

        llamada.enqueue(new Callback() {
            public void onResponse(Call call, Response respuestaServer)
                    throws IOException {

                String respuesta = respuestaServer.body().string();

                Handler manejador = new Handler(Looper.getMainLooper()); //pedimos el principal de la app

                manejador.post(new Runnable() {
                    @Override
                    public void run() {
                        c.setData(respuesta, false);
                    }
                });
            }

            public void onFailure(Call call, IOException e) {

                String respuesta = e.getMessage(); //Fijaros que nos pasan la excepción con el problema.

                Handler manejador = new Handler(Looper.getMainLooper());

                manejador.post(new Runnable() {
                    @Override
                    public void run() {

                        c.setData(respuesta, true);

                    }
                });
            }
        });
    }

    public void deleteCita(int idCita) {
        //Cliente HTTP
        String URL = "http://" + IP + "/eliminar";

        //Añadimos parámetros al GET
        URL += "?idCita=" + idCita;
        OkHttpClient clientHTTP = new OkHttpClient();

        //Petición a realizar al cliente HTTP. Patrón de diseño "Builder". Es decir "poco a poco"
        Request request = new Request.Builder()
                .url(URL)  //dirección web
                .get()     //función http a utilizar, puede ser post, put,...
                .addHeader("accept", "application/json")  //Qué formato queremos
                .build();   //a construir la request!!

        //realizamos la llamada al server, pero en otro thread (con enqueue)
        //Primero, una llamada al server
        Call llamada = clientHTTP.newCall(request);

        //Ponemos la llamada en cola para que salga por la tarjeta de red que tengamos en el móvil activa, y creamos un
        //objeto anónimo CallBack como repsuesta

        llamada.enqueue(new Callback() {
            public void onResponse(Call call, Response respuestaServer)
                    throws IOException {
                //Cogemos el cuerpo de la respuesta
                String respuesta = respuestaServer.body().string();

                // Create a handler that associated with Looper of the main thread
                //Un manejador es un "bucle" en esencia que ejecuta uno a uno todos los procesos de la App
                Handler manejador = new Handler(Looper.getMainLooper()); //pedimos el principal de la app
                // Send a task to the MessageQueue of the main thread
                manejador.post(new Runnable() {
                    @Override
                    public void run() {
                        // Code will be executed on the main thread
                        //Este es código que realmente se ejecuta cuando se recibe la respuesta.
                        c.setData(respuesta, false);
                    }
                });
            }

            public void onFailure(Call call, IOException e) {
                //Cuidado, puede que haya alguna vez un fallo en la respuesta. Entonces entra por aquí
                String respuesta = e.getMessage(); //Fijaros que nos pasan la excepción con el problema.
                //Lo típico es "sacar" el string mensaje de la exceptión y mandarla al sistema principal para
                //que se vea que ha pasado
                Handler manejador = new Handler(Looper.getMainLooper());

                // Send a task to the MessageQueue of the main thread
                manejador.post(new Runnable() {
                    @Override
                    public void run() {
                        // Code will be executed on the main thread

                        c.setData(respuesta, true);

                    }
                });
            }
        });
    }

    public void searchCita(int idCita) {
        //Cliente HTTP
        String URL = "http://" + IP + "/buscar";

        //Añadimos parámetros al GET
        URL += "?idCita=" + idCita;
        OkHttpClient clientHTTP = new OkHttpClient();

        //Petición a realizar al cliente HTTP. Patrón de diseño "Builder". Es decir "poco a poco"
        Request request = new Request.Builder()
                .url(URL)  //dirección web
                .get()     //función http a utilizar, puede ser post, put,...
                .addHeader("accept", "application/json")  //Qué formato queremos
                .build();   //a construir la request!!

        //realizamos la llamada al server, pero en otro thread (con enqueue)
        //Primero, una llamada al server
        Call llamada = clientHTTP.newCall(request);

        //Ponemos la llamada en cola para que salga por la tarjeta de red que tengamos en el móvil activa, y creamos un
        //objeto anónimo CallBack como repsuesta

        llamada.enqueue(new Callback() {
            public void onResponse(Call call, Response respuestaServer)
                    throws IOException {
                //Cogemos el cuerpo de la respuesta
                String respuesta = respuestaServer.body().string();

                // Create a handler that associated with Looper of the main thread
                //Un manejador es un "bucle" en esencia que ejecuta uno a uno todos los procesos de la App
                Handler manejador = new Handler(Looper.getMainLooper()); //pedimos el principal de la app
                // Send a task to the MessageQueue of the main thread
                manejador.post(new Runnable() {
                    @Override
                    public void run() {
                        // Code will be executed on the main thread
                        //Este es código que realmente se ejecuta cuando se recibe la respuesta.
                        c.setData(respuesta, false);
                    }
                });
            }

            public void onFailure(Call call, IOException e) {
                //Cuidado, puede que haya alguna vez un fallo en la respuesta. Entonces entra por aquí
                String respuesta = e.getMessage(); //Fijaros que nos pasan la excepción con el problema.
                //Lo típico es "sacar" el string mensaje de la exceptión y mandarla al sistema principal para
                //que se vea que ha pasado
                Handler manejador = new Handler(Looper.getMainLooper());

                // Send a task to the MessageQueue of the main thread
                manejador.post(new Runnable() {
                    @Override
                    public void run() {
                        // Code will be executed on the main thread

                        c.setData(respuesta, true);

                    }
                });
            }
        });
    }

}
