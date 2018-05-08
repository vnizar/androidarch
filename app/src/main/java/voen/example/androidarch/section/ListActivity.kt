package voen.example.androidarch.section

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_list.*
import voen.example.androidarch.MyApp
import voen.example.androidarch.R
import voen.example.androidarch.adapter.ItemListAdapter
import voen.example.androidarch.dao.RepoDao
import voen.example.androidarch.dao.UserDao
import voen.example.androidarch.entity.ItemList
import voen.example.androidarch.entity.Repo
import voen.example.androidarch.extensions.toast
import voen.example.androidarch.section.RepoActivity.Companion.EXTRA_IS_EDIT
import voen.example.androidarch.section.RepoActivity.Companion.EXTRA_REPO_ID
import javax.inject.Inject

/**
 * Created by voen on 01/05/18.
 */
class ListActivity : AppCompatActivity() {
    @Inject
    lateinit var userDao: UserDao

    @Inject
    lateinit var repoDao: RepoDao

    companion object {
        const val ACTIVITY_EDIT_REPO_CODE = 300
    }

    private val compositeDisposable = CompositeDisposable()
    private val listItem = mutableListOf<ItemList>()
    private val listAdapter by lazy {
        ItemListAdapter(listItem, { item ->
            if (item is Repo) {
                val intent = Intent(this@ListActivity, RepoActivity::class.java).apply {
                    putExtra(EXTRA_IS_EDIT, true)
                    putExtra(EXTRA_REPO_ID, item.id)
                }
                startActivityForResult(intent, ACTIVITY_EDIT_REPO_CODE)
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as MyApp).appComponent.inject(this)
        setContentView(R.layout.activity_list)

        with(rv_user_list) {
            layoutManager = LinearLayoutManager(this@ListActivity)
            adapter = listAdapter
        }

        getData()
    }

    private fun getData() {
        listItem.clear()
        val flowable = Flowable.fromCallable { userDao.getAll() }
                .flatMap { usersList ->
                    Flowable.fromIterable(usersList)
                }
                .flatMap { user ->
                    listItem.add(user)
                    listItem.addAll(Flowable.fromCallable { repoDao.findRepoByUserId(user.id) }.blockingSingle())
                    Flowable.just(listItem)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    listAdapter.notifyDataSetChanged()
                }, {
                    toast("Error retrieve data")
                    it.printStackTrace()
                })
        compositeDisposable.add(flowable)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when {
            requestCode == ACTIVITY_EDIT_REPO_CODE && resultCode == Activity.RESULT_OK -> {
                getData()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}