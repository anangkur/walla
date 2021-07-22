package com.anangkur.wallpaper.utils

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.anangkur.wallpaper.R
import com.anangkur.wallpaper.injection.ViewModelFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import java.io.File
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.regex.Pattern

fun <T : ViewModel> AppCompatActivity.obtainViewModel(viewModelClass: Class<T>) =
    ViewModelProviders.of(this, ViewModelFactory.getInstance()).get(viewModelClass)

fun <T : ViewModel> Fragment.obtainViewModel(viewModelClass: Class<T>) =
    ViewModelProviders.of(this, ViewModelFactory.getInstance()).get(viewModelClass)

fun Activity.showSnackbarLong(message: String){
    Snackbar.make(this.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show()
}

fun Activity.showSnackbarShort(message: String){
    Snackbar.make(this.findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show()
}

fun View.showSnackbarLong(message: String){
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).show()
}

fun View.showSnackbarShort(message: String){
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
}

fun Context.showToastShort(message: String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun ImageView.setImageUrl(url: String){
    Glide.with(this)
        .load(url)
        .apply(RequestOptions().error(R.color.gray))
        .apply(RequestOptions().placeholder(
            createCircularProgressDrawable(
                this.context
            )
        ))
        .apply(RequestOptions().centerCrop())
        .into(this)
}

fun ImageView.setImageUrlDarkBg(url: String){
    Glide.with(this)
        .load(url)
        .apply(RequestOptions().error(R.color.gray))
        .apply(RequestOptions().placeholder(
            createCircularProgressDrawableLight(
                this.context
            )
        ))
        .apply(RequestOptions().centerCrop())
        .into(this)
}

fun hideSoftKeyboard(activity: Activity) {
    val inputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(
        activity.currentFocus!!.windowToken, 0
    )
}

fun RecyclerView.setupRecyclerViewGridEndlessScroll(context: Context, spanCount: Int, adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>, progressView: Int, itemView: Int){
    val mLayoutManager = GridLayoutManager(context, spanCount, GridLayoutManager.VERTICAL, false)
    mLayoutManager.spanSizeLookup = object: GridLayoutManager.SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {
            return when (adapter.getItemViewType(position)){
                progressView -> 1
                itemView -> spanCount
                else -> -1
            }
        }
    }
    this.apply {
        itemAnimator = DefaultItemAnimator()
        layoutManager = mLayoutManager
    }
}

fun RecyclerView.setupRecyclerViewGrid(context: Context, spanCount: Int){
    this.apply {
        itemAnimator = DefaultItemAnimator()
        layoutManager = GridLayoutManager(context, spanCount, GridLayoutManager.VERTICAL, false)
    }
}

fun RecyclerView.setupRecyclerViewLinear(context: Context, orientation: Int){
    this.apply {
        itemAnimator = DefaultItemAnimator()
        layoutManager = LinearLayoutManager(context, orientation, false)
    }
}

fun TabLayout.disableClickTablayout(){
    for (i in 0 until this.tabCount){
        (this.getChildAt(0) as ViewGroup).getChildAt(i).isEnabled = false
    }
}

fun createCircularProgressDrawable(context: Context): CircularProgressDrawable {
    val circularProgressDrawable = CircularProgressDrawable(context)
    circularProgressDrawable.strokeWidth = 4f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()
    return circularProgressDrawable
}

fun createCircularProgressDrawableLight(context: Context): CircularProgressDrawable {
    val circularProgressDrawable = CircularProgressDrawable(context)
    circularProgressDrawable.setColorSchemeColors(Color.WHITE)
    circularProgressDrawable.strokeWidth = 4f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()
    return circularProgressDrawable
}

fun View.visible(){
    this.visibility = View.VISIBLE
}

fun View.gone(){
    this.visibility = View.GONE
}

fun View.invisible(){
    this.visibility = View.INVISIBLE
}

fun View.enable(){
    this.isEnabled = true
}

fun View.disable(){
    this.isEnabled = false
}

fun String.validateEmail(): Boolean{
    val emailPattern = Pattern.compile("^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
    return emailPattern.matcher(this).matches()
}

fun String.validatePassword(): Boolean{
    return this.length >= 6
}

fun String.validateName(): Boolean{
    return this.isNotEmpty()
}

fun String.validatePasswordConfirm(password: String): Boolean{
    return this == password
}

fun Double.currencyFormatToRupiah(): String {
    val kursIndonesia = DecimalFormat.getCurrencyInstance() as DecimalFormat
    val formatRp = DecimalFormatSymbols()

    formatRp.currencySymbol = "Rp. "
    formatRp.monetaryDecimalSeparator = '.'
    formatRp.groupingSeparator = ','

    kursIndonesia.decimalFormatSymbols = formatRp
    return kursIndonesia.format(this)
}

fun SwipeRefreshLayout.startLoading(){
    this.isRefreshing = true
}

fun SwipeRefreshLayout.stopLoading(){
    this.isRefreshing = false
}

fun Spinner.setupSpinner(data: ArrayList<String>, onItemSelected: (Int) -> Unit){
    val arrayAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, data)
    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    this.apply {
        adapter = arrayAdapter
        onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                onItemSelected(position)
            }
        }
    }
}

fun Context.showDialogImagePicker(onClickCamera: () -> Unit, onCLickGallery: () -> Unit) {
    val alertDialog = AlertDialog.Builder(this).create()
    val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_image_picker, null)

    dialogView.apply {
        val btnCamera = findViewById<LinearLayout>(R.id.btn_camera)
        val btnGallery = findViewById<LinearLayout>(R.id.btn_gallery)
        btnCamera.setOnClickListener {
            onClickCamera()
            alertDialog.dismiss()
        }
        btnGallery.setOnClickListener {
            onCLickGallery()
            alertDialog.dismiss()
        }
    }

    alertDialog.setCancelable(true)
    alertDialog.setView(dialogView)
    alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
    alertDialog.show()
}

// Get length of file in bytes
val File.fileSizeInBytes: Long
    get() = length()

// Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
val File.fileSizeInKB: Long
    get() = fileSizeInBytes / 1024

// Convert the KB to MegaBytes (1 MB = 1024 KBytes)
val File.fileSizeInMB: Long
    get() = fileSizeInKB / 1024

// Get length of file in bytes
val ByteArray.fileSizeInBytes: Long
    get() = size.toLong()

// Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
val ByteArray.fileSizeInKB: Long
    get() = fileSizeInBytes / 1024

// Convert the KB to MegaBytes (1 MB = 1024 KBytes)
val ByteArray.fileSizeInMB: Long
    get() = fileSizeInKB / 1024

fun Context.copyToClipboard(text: String){
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText(Const.LABEL_CLIPBOARD, text)
    clipboard.setPrimaryClip(clip)
}