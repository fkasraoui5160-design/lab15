package com.example.lab9;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lab9.classes.Etudiant;
import com.example.lab9.service.EtudiantService;

public class MainActivity extends AppCompatActivity {

    // Déclaration des variables (les éléments de l'écran)
    private EditText nom, prenom, id;
    private Button btnAjouter, btnChercher, btnSupprimer;
    private TextView tvResultat;
    private EtudiantService es;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Récupérer les éléments du layout
        nom = findViewById(R.id.nom);
        prenom = findViewById(R.id.prenom);
        id = findViewById(R.id.id);
        btnAjouter = findViewById(R.id.btnAjouter);
        btnChercher = findViewById(R.id.btnChercher);
        btnSupprimer = findViewById(R.id.btnSupprimer);
        tvResultat = findViewById(R.id.tvResultat);

        // 2. Créer le service (pour parler à la base de données)
        es = new EtudiantService(this);

        // 3. Action du bouton AJOUTER
        btnAjouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajouterEtudiant();
            }
        });

        // 4. Action du bouton CHERCHER
        btnChercher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chercherEtudiant();
            }
        });

        // 5. Action du bouton SUPPRIMER
        btnSupprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                supprimerEtudiant();
            }
        });
    }

    // Méthode pour AJOUTER un étudiant
    private void ajouterEtudiant() {
        String nomValue = nom.getText().toString().trim();
        String prenomValue = prenom.getText().toString().trim();

        // Vérifier que les champs ne sont pas vides
        if (nomValue.isEmpty() || prenomValue.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir le nom et le prénom", Toast.LENGTH_SHORT).show();
            return;
        }

        // Créer et ajouter l'étudiant
        Etudiant e = new Etudiant(nomValue, prenomValue);
        es.create(e);

        // Vider les champs
        nom.setText("");
        prenom.setText("");

        // Message de confirmation
        Toast.makeText(this, "Étudiant ajouté avec succès !", Toast.LENGTH_SHORT).show();
    }

    // Méthode pour CHERCHER un étudiant par ID
    private void chercherEtudiant() {
        String idValue = id.getText().toString().trim();

        // Vérifier que l'ID n'est pas vide
        if (idValue.isEmpty()) {
            Toast.makeText(this, "Veuillez saisir un ID", Toast.LENGTH_SHORT).show();
            return;
        }

        int idInt = Integer.parseInt(idValue);
        Etudiant e = es.findById(idInt);

        // Afficher le résultat
        if (e == null) {
            tvResultat.setText("Aucun étudiant trouvé avec l'ID : " + idInt);
            Toast.makeText(this, "Étudiant introuvable", Toast.LENGTH_SHORT).show();
        } else {
            tvResultat.setText("ID: " + e.getId() + " | " + e.getNom() + " " + e.getPrenom());
        }
    }

    // Méthode pour SUPPRIMER un étudiant
    private void supprimerEtudiant() {
        String idValue = id.getText().toString().trim();

        // Vérifier que l'ID n'est pas vide
        if (idValue.isEmpty()) {
            Toast.makeText(this, "Veuillez saisir un ID", Toast.LENGTH_SHORT).show();
            return;
        }

        int idInt = Integer.parseInt(idValue);
        Etudiant e = es.findById(idInt);

        // Vérifier que l'étudiant existe
        if (e == null) {
            Toast.makeText(this, "Aucun étudiant avec l'ID : " + idInt, Toast.LENGTH_SHORT).show();
            return;
        }

        // Supprimer
        es.delete(e);
        tvResultat.setText("Étudiant supprimé !");
        id.setText("");
        Toast.makeText(this, "Étudiant supprimé avec succès", Toast.LENGTH_SHORT).show();
    }
}