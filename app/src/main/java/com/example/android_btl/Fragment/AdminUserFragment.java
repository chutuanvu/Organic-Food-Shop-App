package com.example.android_btl.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import com.example.android_btl.Adapter.AdminUserAdapter;
import com.example.android_btl.DAO.UserDAO;
import com.example.android_btl.Model.User;
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

public class AdminUserFragment extends Fragment {

    private ActivityResultLauncher<Intent> pickImageLauncher;
    private ActivityResultLauncher<Intent> takePictureLauncher;
    private byte[] img = null;
    private List<User> userList;
    private ImageView imvImg;
    private MaterialButton btnAdd, btnUpdate, btnClean;
    private TextInputEditText edtUserName, edtName, edtEmail, edtPassword;
    private RecyclerView rvUser;
    private Spinner spinnerRoleUser;
    private AdminUserAdapter adapter;
    private UserDAO userDAO;
    private String id;
    private ImageButton imgBtnGallery, imgBtnCamera;

    public AdminUserFragment() {}

    public static AdminUserFragment newInstance() {
        return new AdminUserFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userDAO = new UserDAO(getContext());
        pickImageLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Uri selectedImageUri = result.getData().getData();
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(
                                    requireContext().getContentResolver(), selectedImageUri);
                            img = covert(bitmap);
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
                        img = covert(imageBitmap);
                        imvImg.setImageBitmap(imageBitmap);
                    }
                }
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_fragment_user, container, false);
        imgBtnGallery = view.findViewById(R.id.imgBtnGallery);
        imgBtnCamera = view.findViewById(R.id.imgBtnCamera);
        imvImg = view.findViewById(R.id.imvImg);

        btnAdd = view.findViewById(R.id.btnAdd);
        btnUpdate = view.findViewById(R.id.btnUpdate);
        btnClean = view.findViewById(R.id.btnClean);

        edtUserName = view.findViewById(R.id.edtUserName);
        edtName = view.findViewById(R.id.edtName);
        edtEmail = view.findViewById(R.id.edtEmail);
        edtPassword = view.findViewById(R.id.edtPassword);
        spinnerRoleUser = view.findViewById(R.id.spnRoleUser);
        rvUser = view.findViewById(R.id.rvUser);
        String[] roles = {"admin", "user"};
        ArrayAdapter<String> roleAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, roles);
        roleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRoleUser.setAdapter(roleAdapter);
        rvUser.setLayoutManager(new LinearLayoutManager(getContext()));
        loadUserList();
        btnAdd.setOnClickListener(v -> {
            String username = edtUserName.getText().toString().trim();
            String name = edtName.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();
            String role = spinnerRoleUser.getSelectedItem().toString();
            if (username.isEmpty() || name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }
            if (userDAO.checkUserName(username)) {
                Toast.makeText(getContext(), "Tên người dùng đã tồn tại", Toast.LENGTH_SHORT).show();
                return;
            }
            User user = new User(
                    UUID.randomUUID().toString(),
                    username,
                    name,
                    password,
                    email,
                    img,
                    role,
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()),
                    null
            );
            userDAO.addUser(user);
            Toast.makeText(getContext(), "Thêm người dùng thành công", Toast.LENGTH_SHORT).show();
            loadUserList();
        });
        btnClean.setOnClickListener(v -> {
            edtUserName.setText("");
            edtName.setText("");
            edtEmail.setText("");
            edtPassword.setText("");
            spinnerRoleUser.setSelection(0);
            imvImg.setImageResource(R.drawable.ic_default_profile);
            img = null;
        });
        btnUpdate.setOnClickListener(v -> {
            String username = edtUserName.getText().toString().trim();
            String name = edtName.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();
            String role = spinnerRoleUser.getSelectedItem().toString();
            if (username.isEmpty() || name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }
            String updatedTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
            User updatedUser = new User(
                    id,
                    username,
                    name,
                    password,
                    email,
                    img,
                    role,
                    updatedTime,
                    null
            );
            userDAO.updateUser(updatedUser);
            Toast.makeText(getContext(), "Cập nhật người dùng thành công", Toast.LENGTH_SHORT).show();
            loadUserList();
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

    private byte[] covert(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void loadUserList() {
        Cursor cursor = userDAO.getAllUsers();
        userList = userDAO.getUsersFromCursor(cursor);

        if (adapter == null) {
            adapter = new AdminUserAdapter(getContext(), userList, new AdminUserAdapter.OnUserClickListener() {
                @Override
                public void onUserClick(User user) {
                    id = user.getUser_id();
                    edtUserName.setText(user.getUser_name());
                    edtName.setText(user.getName());
                    edtEmail.setText(user.getEmail());
                    spinnerRoleUser.setSelection(getRolePosition(user.getRole()));
                    img = user.getImg();
                    if (img != null) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
                        imvImg.setImageBitmap(bitmap);
                    }
                }
            }, id -> {
                deleteUser(id);
            });
            rvUser.setAdapter(adapter);
        } else {
            adapter.updateUserList(userList);
        }

        if (cursor != null) {
            cursor.close();
        }
    }

    private void deleteUser(String id) {
        userDAO.deleteUser(id);
        loadUserList();
        Toast.makeText(getContext(), "Đã xóa người dùng", Toast.LENGTH_SHORT).show();
    }

    private int getRolePosition(String role) {
        return "admin".equals(role) ? 0 : 1;
    }
}
