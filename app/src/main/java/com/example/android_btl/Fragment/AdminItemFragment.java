package com.example.android_btl.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_btl.Adapter.AdminItemAdapter;
import com.example.android_btl.DAO.CategoryDAO;
import com.example.android_btl.DAO.ItemDAO;
import com.example.android_btl.Model.Category;
import com.example.android_btl.Model.Item;
import com.example.android_btl.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class AdminItemFragment extends Fragment {
    private List<Category> categoryList;
    private ActivityResultLauncher<Intent> pickImageLauncher;
    private ActivityResultLauncher<Intent> takePictureLauncher;
    private byte[] img = null;
    private List<Item> itemList;
    private ImageView imvImg;
    private MaterialButton btnAdd, btnUpdate, btnClean;
    private TextInputEditText edtName, edtPrice, edtQuantity, edtDescription;
    private RecyclerView rvItem;
    private Spinner spnCategory;
    private AdminItemAdapter adapter;
    private ItemDAO itemDAO;
    private CategoryDAO categoryDAO;
    private String itemId;
    private String categoryId;
    private ImageButton imgBtnGallery, imgBtnCamera;

    public AdminItemFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemDAO = new ItemDAO(getContext());
        categoryDAO = new CategoryDAO(getContext());
        pickImageLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Uri selectedImageUri = result.getData().getData();
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(
                                    requireContext().getContentResolver(), selectedImageUri);
                            img = convert(bitmap);
                            imvImg.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        takePictureLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Bundle extras = result.getData().getExtras();
                        Bitmap imageBitmap = (Bitmap) extras.get("data");
                        img = convert(imageBitmap);
                        imvImg.setImageBitmap(imageBitmap);
                    }
                }
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_fragment_item, container, false);

        imgBtnGallery = view.findViewById(R.id.imgBtnGallery);
        imgBtnCamera = view.findViewById(R.id.imgBtnCamera);
        imvImg = view.findViewById(R.id.imvImg);

        btnAdd = view.findViewById(R.id.btnAdd);
        btnUpdate = view.findViewById(R.id.btnUpdate);
        btnClean = view.findViewById(R.id.btnClean);

        edtName = view.findViewById(R.id.edtName);
        edtPrice = view.findViewById(R.id.edtPrice);
        edtQuantity = view.findViewById(R.id.edtQuantity);
        edtDescription = view.findViewById(R.id.edtDescription);

        spnCategory = view.findViewById(R.id.spnCategory);
        rvItem = view.findViewById(R.id.rvItem);
        rvItem.setLayoutManager(new LinearLayoutManager(getContext()));
        loadItemList();
        Cursor cursor = categoryDAO.getAllCategory();
        categoryList = categoryDAO.getCategorysFromCursor(cursor);
        ArrayAdapter<Category> categoryAdapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_spinner_item,
                categoryList
        );
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCategory.setAdapter(categoryAdapter);
        spnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Category selectedCategory = (Category) parent.getItemAtPosition(position);
                categoryId = selectedCategory.getCategory_id();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnAdd.setOnClickListener(v -> {
            String itemname = edtName.getText().toString().trim();
            String priceStr = edtPrice.getText().toString().trim();
            String quantityStr = edtQuantity.getText().toString().trim();
            int price = 0, quantity = 0;
            if (priceStr.isEmpty() || quantityStr.isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng nhập giá và số lượng", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                price = Integer.parseInt(priceStr);
                quantity = Integer.parseInt(quantityStr);
            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Vui lòng nhập số hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }
            String description = edtDescription.getText().toString().trim();
            if (itemname.isEmpty() || description.isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }
            Item item = new Item(
                    UUID.randomUUID().toString(),
                    itemname,
                    description,
                    price,
                    quantity,
                    categoryId,
                    img,
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()),
                    null
            );
            itemDAO.addItem(item);
            Toast.makeText(getContext(), "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
            loadItemList();
        });
        btnClean.setOnClickListener(v -> {
            edtName.setText("");
            edtDescription.setText("");
            edtQuantity.setText("");
            edtPrice.setText("");
            spnCategory.setSelection(0);
            imvImg.setImageResource(R.drawable.ic_default_profile);
            img = null;
        });
        btnUpdate.setOnClickListener(v -> {
            String itemname = edtName.getText().toString().trim();
            int price = 0;
            int quantity = 0;
            try {
                price = Integer.parseInt(edtPrice.getText().toString().trim());
                quantity = Integer.parseInt(edtQuantity.getText().toString().trim());
            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Vui lòng nhập số hợp lệ", Toast.LENGTH_SHORT).show();
            }
            String description = edtDescription.getText().toString().trim();
            if (itemname.isEmpty() || description.isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }
            Item updatedItem = new Item(
                    itemId,
                    itemname,
                    description,
                    price,
                    quantity,
                    categoryId,
                    img,
                    itemDAO.getItemById(itemId).getCreated_time(),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date())
                    );
            itemDAO.updateItem(updatedItem);
            Toast.makeText(getContext(), "Cập nhật sản phẩm thành công", Toast.LENGTH_SHORT).show();
            loadItemList();
        });
        imgBtnCamera.setOnClickListener(v -> openCamera());
        imgBtnGallery.setOnClickListener(v -> openGallery());
        return view;
    }

    private void openCamera() {
        if (takePictureLauncher != null) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(requireContext().getPackageManager()) != null) {
                takePictureLauncher.launch(takePictureIntent);
            } else {
                Toast.makeText(getContext(), "Không thể mở camera", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openGallery() {
        if (pickImageLauncher != null) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            pickImageLauncher.launch(intent);
        }
    }

    private byte[] convert(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void loadItemList() {
        Cursor cursor = itemDAO.getAllItem();
        itemList = itemDAO.getItemsFromCursor(cursor);

        if (adapter == null) {
            adapter = new AdminItemAdapter(getContext(), itemList, new AdminItemAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(Item item) {
                    itemId = item.getItem_id();
                    edtName.setText(item.getItem_name());
                    edtPrice.setText(String.valueOf(item.getPrice()));
                    edtDescription.setText(item.getDescription());
                    edtQuantity.setText(String.valueOf(item.getQuantity()));
                    img = item.getImg();
                    if (img != null) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
                        imvImg.setImageBitmap(bitmap);
                    } else {
                        imvImg.setImageResource(R.drawable.ic_default_profile);
                    }
                }
            }, itemId -> {
                deleteItem(itemId);
            },categoryDAO);
            rvItem.setAdapter(adapter);
        } else {
            adapter.updateItemList(itemList);
        }

        if (cursor != null) {
            cursor.close();
        }
    }

    private void deleteItem(String id) {
        itemDAO.deleteItem(id);
        loadItemList();
        Toast.makeText(getContext(), "Đã xóa sản phẩm", Toast.LENGTH_SHORT).show();
    }
}
