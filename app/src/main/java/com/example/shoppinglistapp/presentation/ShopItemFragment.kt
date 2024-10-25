package com.example.shoppinglistapp.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglistapp.R
import com.example.shoppinglistapp.domain.ShopItem
import com.google.android.material.textfield.TextInputLayout

class ShopItemFragment : Fragment() {

    private lateinit var viewModel: ShopItemViewModel

    private lateinit var tilName: TextInputLayout
    private lateinit var tilCount: TextInputLayout
    private lateinit var etName: EditText
    private lateinit var etCount: EditText
    private lateinit var btnSave: Button

    // screenMode хранит мод (значение intent)
    // по умолчанию значение не известно
    private var screenMode: String = UNKNOWN_MODE

    // shopItemID хранит id (значение intent)
    // по умолчанию значение не известно (-1)
    private var shopItemID: Int = ShopItem.UNDEFIND_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //проверяем параметры и присваиваем им значения
        // это нужно делать в onCreate когда надо что бы в других
        // методах уже были значения из метода parseParams
        parseParams()
    }

    // Метод жизненного цикла фрагмента onCreateView нужен для того, чтобы создать View из макета
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shop_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // инициализируем view по id
        createViews(view)

        // присваиваем значение viewModel
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]

        // проверяем mod сцены и в зависимости от этого генерируем логику
        createSceneMode()

        // создаем наблюдатель за полями ввода
        textChangeListener()

        // метод где лежат все наблюдатели
        observedViewModel()

    }

    private fun createSceneMode() {
        // в зависимости от мода запускаем нужное нам поведение экрана
        when (screenMode) {
            MODE_EDIT -> runEditScene()
            MODE_ADD -> runAddScene()
        }
    }

    private fun runAddScene() {
        Log.d("screenMode", "runAddScene")

        btnSave.setOnClickListener {
            viewModel.addShopItem(etName.text?.toString(), etCount.text.toString())
        }
    }

    private fun runEditScene() {
        Log.d("screenMode", "runEditScene")

        viewModel.getShopItem(shopItemID)

        // В качестве Observer в метод observe нужно передавать объект типа
        // LifecycleOwner. Во фрагментах в качестве LifecycleOwner нужно
        // передавать viewLifecycleOwner вместо this, т.к. ЖЦ View и фрагмента
        // отличаются и фрагмент может жить дольше, чем View, которая с ним
        // связана.
        viewModel.shopItem.observe(viewLifecycleOwner) {
            etName.setText(it.name)
            etCount.setText(it.count.toString())
        }

        btnSave.setOnClickListener {
            viewModel.editShopItem(etName.text?.toString(), etCount.text.toString())
        }
    }

    private fun observedViewModel() {
        // Отображает ошибку если поле пустое
        viewModel.errorInputName.observe(viewLifecycleOwner) {
            val message = if (it) {
                "Поле не должно быть пустым"
            } else {
                null
            }
            tilName.error = message
        }

        // Отображает ошибку если поле пустое
        viewModel.errorInputCount.observe(viewLifecycleOwner) {
            val message = if (it) {
                "Поле не должно быть пустым"
            } else {
                null
            }
            tilCount.error = message
        }

        viewModel.finishFlow.observe(viewLifecycleOwner) {
            // onBackPressedDispatcher делает тоже самое что и при нажатии
            // кнопки назад на телефоне
            activity?.onBackPressedDispatcher
            // activity - получаем ссылку на activity к которой прикреплен фрагмент
            // так же можно сделать то же самое и через requireActivity но разница в том
            // что activity возвращает null тип а requireActivity notnull
        }
    }

    // проверка что все параметры были переданы
    private fun parseParams() {
        // requireArguments - если мы уверены что пераметры были переданы
        // к этому моменту
        val arg = requireArguments()
        // если arg не будет ключа (параметра) SCREEN_MODE
        if (!arg.containsKey(SCREEN_MODE)) throw RuntimeException("screen mode is absent")
        // создаем мод по ключу
        val mode = arg.getString(SCREEN_MODE)
        // если этот мод не имеет значений MODE_EDIT и MODE_ADD
        if (mode != MODE_EDIT && mode != MODE_ADD) throw RuntimeException("mode is absent")
        // если имеет то присваиваем значение
        screenMode = mode
        // если мод редактирования
        if (mode == MODE_EDIT) {
            // то там обязательно должен присутствовать параметр id
            if (!arg.containsKey(SHOP_ITEM_ID)) throw RuntimeException("id is absent")
            // присваиваем id из intent и значение по умолчанию
            shopItemID = arg.getInt(SHOP_ITEM_ID, ShopItem.UNDEFIND_ID)
        }
    }

    // view нужен для достука к методу по поиске элемента
    // а получаем мы view из метода жизненного цикла
    // onViewCreated
    private fun createViews(view: View) {
        tilName = view.findViewById(R.id.til_name)
        tilCount = view.findViewById(R.id.til_count)
        etName = view.findViewById(R.id.et_name)
        etCount = view.findViewById(R.id.et_count)
        btnSave = view.findViewById(R.id.btn_save)
    }

    // наблюдатель за вводом текста
    // сбратывает error если начинаем вводить значения в поле
    private fun textChangeListener() {
        etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        etCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputCount()
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }


    companion object {
        private const val SCREEN_MODE = "extra_mode"
        private const val SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val UNKNOWN_MODE = "unknown"
        private const val FIELD_ERROR = "Error"

        // передаем нужный нам  Fragment в зависимости от метода
        fun newInstanceAdd(): ShopItemFragment {
            // apply - вызывается у объекта и позволяет в лямбде
            // применить К НЕМУ ЖЕ какие то свойства и потом ВОЗВРАЩАЕТ
            // этот же объект с примененнными свойствами
            return ShopItemFragment().apply {
                // arguments - позволяет передать аргументы в фрагметн
                // и при его пересоздании эти аргументы будут переданы снова
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_ADD)
                }
            }
        }

        fun newInstanceEdit(id: Int): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_EDIT)
                    putInt(SHOP_ITEM_ID, id)
                }
            }
        }
    }
}
