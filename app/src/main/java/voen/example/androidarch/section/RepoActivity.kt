package voen.example.androidarch.section

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ArrayAdapter
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_repo.*
import voen.example.androidarch.MyApp
import voen.example.androidarch.R
import voen.example.androidarch.dao.RepoDao
import voen.example.androidarch.dao.UserDao
import voen.example.androidarch.entity.Repo
import voen.example.androidarch.entity.User
import voen.example.androidarch.extensions.toast
import java.util.*
import javax.inject.Inject

/**
 * Created by voen on 01/05/18.
 */
class RepoActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_IS_EDIT = "is_edit"
        const val EXTRA_REPO_ID = "repo_id"
    }

    @Inject
    lateinit var userDao: UserDao

    @Inject
    lateinit var repoDao: RepoDao

    private val userList = mutableListOf<User>()
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as MyApp).appComponent.inject(this)

        setContentView(R.layout.activity_repo)

        initUserList()
        val editMode = intent.getBooleanExtra(EXTRA_IS_EDIT, false)
        if (editMode) {
            setupEditMode()
        } else {
            setupInsertMode()
        }
    }

    private fun setupInsertMode() {
        bt_delete_repo.visibility = View.GONE
        bt_save_repo.setOnClickListener {
            if (validateFields()) {
                val completable = Completable.fromAction { repoDao.insert(Repo(UUID.randomUUID().toString(), et_repo_name.text.toString(), et_repo_url.text.toString(), userList[spinner_users.selectedItemPosition].id)) }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            toast("Repo Added")
                            clearTextFields()
                        }, {
                            toast("Error insert data")
                            it.printStackTrace()
                        })
                compositeDisposable.add(completable)
            }
        }
    }

    private fun setupEditMode() {
        spinner_users.visibility = View.GONE
        var repo: Repo? = null
        val repoId = intent.getStringExtra(EXTRA_REPO_ID)
        val flowable = Flowable.fromCallable { repoDao.findRepoById(repoId) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    repo = it
                    et_repo_name.setText(it.name)
                    et_repo_url.setText(it.url)
                }, {
                    toast("Error retrieve data")
                    it.printStackTrace()
                })
        compositeDisposable.add(flowable)

        bt_save_repo.setOnClickListener {
            if (validateFields()) {
                repo?.let {
                    val updatedRepo = it.copy(name = et_repo_name.text.toString(), url = et_repo_url.text.toString())
                    val completable = Completable.fromAction { repoDao.update(updatedRepo) }
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({
                                toast("Repo Updated")
                                setResult(Activity.RESULT_OK)
                                finish()
                            }, {
                                toast("Error insert data")
                            })
                    compositeDisposable.add(completable)
                }
            }
        }

        bt_delete_repo.setOnClickListener {
            repo?.let {
                val completable = Completable.fromAction { repoDao.delete(it) }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            toast("Repo Deleted")
                            setResult(Activity.RESULT_OK)
                            finish()
                        }, {
                            toast("Error delete data")
                        })
                compositeDisposable.add(completable)
            }
        }
    }

    private fun validateFields() = et_repo_name.text.isNotEmpty() && et_repo_url.text.isNotEmpty()

    private fun clearTextFields() {
        et_repo_name.text.clear()
        et_repo_url.text.clear()
    }

    private fun initUserList() {
        val flowable = Flowable.fromCallable { userDao.getAll() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    userList.addAll(it)
                    spinner_users.adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, it.map { it.firstName }).apply {
                        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    }
                }, {
                    toast("Error retrieve data")
                    it.printStackTrace()
                })
        compositeDisposable.add(flowable)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}