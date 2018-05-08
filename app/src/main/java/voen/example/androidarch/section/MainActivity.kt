package voen.example.androidarch.section

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import voen.example.androidarch.MyApp
import voen.example.androidarch.R
import voen.example.androidarch.dao.UserDao
import voen.example.androidarch.entity.User
import voen.example.androidarch.extensions.toast
import java.util.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var userDao: UserDao

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (application as MyApp).appComponent.inject(this)

        bt_data.setOnClickListener {
            if (et_firstname.text.isNotEmpty() && et_lastname.text.isNotEmpty() && et_age.text.isNotEmpty()) {
                val completable = Completable.fromAction { userDao.insertAll(User(UUID.randomUUID().toString(), et_firstname.text.toString(), et_lastname.text.toString())) }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            toast("User ${et_firstname.text} Added")
                            clearTextFields()
                        }, {
                            toast("Error insert data")
                            it.printStackTrace()
                        })
                compositeDisposable.add(completable)
            } else {
                toast("Please fill all fields")
            }
        }
    }

    private fun clearTextFields() {
        et_firstname.text.clear()
        et_lastname.text.clear()
        et_age.text.clear()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_user_list -> {
                startActivity(Intent(this, ListActivity::class.java))
            }
            R.id.action_add_repo -> {
                startActivity(Intent(this, RepoActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}
