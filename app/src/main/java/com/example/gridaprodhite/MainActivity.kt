package com.example.gridaprodhite

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.gridaprodhite.data.DataSource
import com.example.gridaprodhite.model.Makeup
import com.example.gridaprodhite.ui.theme.GridAprodhiteTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Mengatur tema GridAprodhiteTheme untuk seluruh tampilan Activity
            GridAprodhiteTheme {
                // Membungkus tampilan dalam Surface dengan latar belakang sesuai tema
                Surface(
                    modifier = Modifier.fillMaxSize(),  // Mengisi seluruh layar dengan tampilan ini
                    color = MaterialTheme.colorScheme.background  // Mengatur warna latar belakang sesuai tema
                ) {
                    // Menampilkan komponen MakeupGrid dalam tampilan Activity
                    MakeupGrid(
                        modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))  // Memberikan padding di sekitar MakeupGrid
                    )
                }
            }
        }
    }
}


@Composable
fun MakeupGrid(modifier: Modifier = Modifier) {
    Column {
        Spacer(modifier = Modifier.height(20.dp)) // Spacer dengan tinggi 20dp, digunakan untuk memberi jarak atas

        // Box untuk mengatur teks "Product Recommendation" di tengah horizontal
        Box(
            modifier = Modifier.fillMaxWidth(), // Mengisi lebar maksimum
            contentAlignment = Alignment.Center // Aligment kontennya di tengah
        ) {
            Text(
                text = "Product Recommendation", // Teks yang ditampilkan
                fontWeight = FontWeight.Bold, // Ketebalan teks
                color = Color(0xFF274690), // Warna teks (biru muda)
                fontSize = 24.sp, // Ukuran font 24sp
                textAlign = TextAlign.Center // Teks diatur di tengah secara horizontal
            )
        }

        Spacer(modifier = Modifier.height(10.dp)) // Spacer dengan tinggi 10dp, memberi jarak ke elemen berikutnya

        // Menggunakan LazyVerticalGrid untuk menampilkan item dalam tata letak grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(2), // Mengatur jumlah kolom dalam grid menjadi 2
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
            modifier = modifier
        ) {
            // Menggunakan items untuk mengisi tata letak grid dengan data dari DataSource.makeups
            items(DataSource.makeups) { makeup ->
                // Menampilkan komponen MakeupCard untuk setiap elemen dalam DataSource.makeups
                MakeupCard(makeup)
            }
        }
    }
}

@Composable
fun MakeupCard(makeup: Makeup) {
    // Membuat mutable state untuk mengontrol visibilitas dialog
    var showDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth(0.45f)  // Mengisi lebar maksimum dengan 45% dari parent
            .padding(8.dp)  // Memberi jarak dari elemen lain
    ) {
        Column(
            modifier = Modifier.padding(8.dp)  // Memberi jarak dalam Card
        ) {
            Image(
                painter = painterResource(id = makeup.image), // Menampilkan gambar produk
                contentDescription = null,  // Deskripsi konten gambar kosong
                modifier = Modifier
                    .fillMaxWidth()  // Mengisi lebar maksimum
                    .height(200.dp)   // Menentukan tinggi gambar
                    .clip(shape = RoundedCornerShape(8.dp)),  // Memberi sudut melengkung pada gambar
                contentScale = ContentScale.Crop  // Mengatur scaling untuk gambar
            )
            Spacer(modifier = Modifier.height(8.dp))  // Memberi jarak ke teks berikutnya
            Text(
                text = stringResource(id = makeup.name),  // Menampilkan nama produk
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            )
            Spacer(modifier = Modifier.height(4.dp)) // memberi jarak ke teks harga
            Text(
                text = stringResource(id = makeup.brand),  //menampilan harga
                style = TextStyle(
                    fontSize = 12.sp,  // Ukuran font
                    fontWeight = FontWeight.Normal,  // Ketebalan font
                    color = Color.Gray  // Warna teks
                )
            )

            // Tombol untuk menampilkan dialog
            Button(
                onClick = { showDialog = true },  // Ketika tombol diklik, setelah dialog ditampilkan
                modifier = Modifier
                    .fillMaxWidth()  // Mengisi lebar maksimum
                    .padding(top = 8.dp)  // Memberi jarak dari elemen di atasnya
            ) {
                Text(text = "Lihat Detail")  // Teks yang akan ditampilkan pada tombol
            }

            // Dialog yang akan muncul saat tombol diklik
            if (showDialog) {
                Dialog(
                    onDismissRequest = { showDialog = false }, // Ketika dialog ditutup, setelah digeser atau diklik di luar dialog
                    properties = DialogProperties(dismissOnClickOutside = true) // Mengizinkan dialog ditutup dengan mengklik di luar dialog
                ) {
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background) // Memberi latar belakang warna background
                            .fillMaxSize()  // Mengisi layar penuh
                    ) {
                        // Isi dialog (informasi card)
                        Column(
                            modifier = Modifier
                                .padding(16.dp)  // Memberi jarak dari batas dialog
                                .fillMaxWidth()  // Mengisi lebar maksimum
                                .fillMaxHeight(0.5f)  // Mengisi setengah tinggi layar
                                .clip(MaterialTheme.shapes.medium)  // Memberi efek sudut melengkung pada elemen
                                .background(MaterialTheme.colorScheme.surface)  // Memberi latar belakang warna surface
                                .padding(16.dp),  // Memberi jarak dalam dialog
                            verticalArrangement = Arrangement.spacedBy(8.dp)  // Memberi jarak antar elemen
                        ) {
                            Text(
                                text = stringResource(id = makeup.name), // Menampilkan nama produk
                                style = TextStyle(
                                    fontSize = 20.sp, // Ukuran font
                                    fontWeight = FontWeight.Bold,  // Ketebalan font
                                    color = Color.Black  // Warna teks
                                )
                            )
                            Text(
                                text = stringResource(id = makeup.brand),  // Menampilkan harga merek produk
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = Color.Gray
                                )
                            )
                            Spacer(modifier = Modifier.height(8.dp)) // Memberi jarak antar elemen
                        }

                        // Tombol tutup dialog
                        IconButton(
                            onClick = { showDialog = false }, // Ketika tombol ditutup, dialog ditutup
                            modifier = Modifier
                                .align(Alignment.TopEnd)  // Menempatkan tombol di sudut kanan atas
                                .padding(8.dp)  // Memberi jarak dari batas
                        ) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = null)  // Menggunakan ikon penutup
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    // Menggunakan tema GridAprodhiteTheme untuk tampilan komposisi ini
    GridAprodhiteTheme {
        // Mengatur tata letak komponen utama dalam Column
        Column(
            modifier = Modifier.fillMaxSize(),  // Mengisi seluruh layar dengan tata letak ini
            verticalArrangement = Arrangement.Center,  // Menengahkan secara vertikal kontennya
            horizontalAlignment = Alignment.CenterHorizontally  // Menengahkan secara horizontal kontennya
        ) {
            // Menampilkan komponen MakeupGrid dalam tata letak ini
            MakeupGrid(
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))  // Memberikan padding di sekitar MakeupGrid
            )
        }
    }
}
