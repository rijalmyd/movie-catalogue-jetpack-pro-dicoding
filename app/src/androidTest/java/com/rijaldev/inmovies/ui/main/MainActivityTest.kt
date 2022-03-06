package com.rijaldev.inmovies.ui.main

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.rijaldev.inmovies.R
import com.rijaldev.inmovies.utils.EspressoIdlingResource
import com.rijaldev.inmovies.utils.RecyclerViewItemCountAssertion
import org.junit.After
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class MainActivityTest {

    @Before
    fun setUp() {
        ActivityScenario.launch(MainActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun aLoadMovies() {
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        onView(withId(R.id.tv_sort_asc)).perform(click())
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        onView(withId(R.id.tv_sort_desc)).perform(click())
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        onView(withId(R.id.tv_sort_rating)).perform(click())
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        onView(withId(R.id.tv_sort_default)).perform(click())
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
    }

    @Test
    fun bLoadDetailMovie() {
        onView(withId(R.id.rv_movie)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.image_poster)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_tagline)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_duration)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_description)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_genre)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_rating)).check(matches(isDisplayed()))
        onView(withId(R.id.scrollView)).perform(ViewActions.swipeUp())
        onView(withId(R.id.tv_status)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_language)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_date_release)).check(matches(isDisplayed()))
    }

    @Test
    fun cLoadMovieSnackBar() {
        onView(withId(R.id.rv_movie)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.tv_genre)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_genre)).perform(click())
        onView(withId(com.google.android.material.R.id.snackbar_text)).check(matches(isDisplayed()))
    }

    @Test
    fun dLoadTvShows() {
        onView(withText("TV SHOWS")).perform(click())
        onView(withId(R.id.rv_tv)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tv)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        onView(withId(R.id.tv_sort_asc)).perform(click())
        onView(withId(R.id.rv_tv)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tv)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        onView(withId(R.id.tv_sort_desc)).perform(click())
        onView(withId(R.id.rv_tv)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tv)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        onView(withId(R.id.tv_sort_rating)).perform(click())
        onView(withId(R.id.rv_tv)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tv)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        onView(withId(R.id.tv_sort_default)).perform(click())
        onView(withId(R.id.rv_tv)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tv)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
    }

    @Test
    fun eLoadDetailTvShow() {
        onView(withText("TV SHOWS")).perform(click())
        onView(withId(R.id.rv_tv)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tv)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        onView(withId(R.id.rv_tv)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.image_poster)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_tagline)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_duration)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_description)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_genre)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_rating)).check(matches(isDisplayed()))
        onView(withId(R.id.scrollView)).perform(ViewActions.swipeUp())
        onView(withId(R.id.tv_status)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_language)).check(matches(isDisplayed()))
    }

    @Test
    fun fLoadTvShowSnackBar() {
        onView(withText("TV SHOWS")).perform(click())
        onView(withId(R.id.rv_tv)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tv)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        onView(withId(R.id.rv_tv)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.tv_genre)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_genre)).perform(click())
        onView(withId(com.google.android.material.R.id.snackbar_text)).check(matches(isDisplayed()))
    }

    @Test
    fun gShowEmptyFavoriteMovie() {
        onView(withId(R.id.favoriteFragment)).perform(click())
        onView(withId(R.id.iv_no_movie)).check(matches(isDisplayed()))
    }

    @Test
    fun hAddOneMovieToFavorite() {
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        onView(withId(R.id.rv_movie)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.content_detail)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_add_favorite)).perform(click())
    }

    @Test
    fun iShowEmptyFavoriteTvShow() {
        onView(withId(R.id.favoriteFragment)).perform(click())
        onView(withText("TV SHOWS")).perform(click())
        onView(withId(R.id.iv_no_tv_show)).check(matches(isDisplayed()))
    }

    @Test
    fun jAddOneTvShowToFavorite() {
        onView(withText("TV SHOWS")).perform(click())
        onView(withId(R.id.rv_tv)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tv)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        onView(withId(R.id.rv_tv)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.content_detail)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_add_favorite)).perform(click())
    }

    @Test
    fun kLoadFavoriteMovies() {
        onView(withId(R.id.favoriteFragment)).perform(click())
        onView(withId(R.id.rv_fav_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_fav_movie)).check(RecyclerViewItemCountAssertion(1))
    }

    @Test
    fun lLoadDetailFavoriteMovie() {
        onView(withId(R.id.favoriteFragment)).perform(click())
        onView(withId(R.id.rv_fav_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_fav_movie)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.image_poster)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_tagline)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_duration)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_description)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_genre)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_rating)).check(matches(isDisplayed()))
        onView(withId(R.id.scrollView)).perform(ViewActions.swipeUp())
        onView(withId(R.id.tv_status)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_language)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_date_release)).check(matches(isDisplayed()))
    }

    @Test
    fun mLoadSnackBarFavoriteMovie() {
        onView(withId(R.id.favoriteFragment)).perform(click())
        onView(withId(R.id.rv_fav_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_fav_movie)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.tv_genre)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_genre)).perform(click())
        onView(withId(com.google.android.material.R.id.snackbar_text)).check(matches(isDisplayed()))
    }

    @Test
    fun nLoadFavoriteTvShows() {
        onView(withId(R.id.favoriteFragment)).perform(click())
        onView(withText("TV SHOWS")).perform(click())
        onView(withId(R.id.rv_fav_tv)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_fav_tv)).check(RecyclerViewItemCountAssertion(1))
    }

    @Test
    fun oLoadDetailFavoriteTvShow() {
        onView(withId(R.id.favoriteFragment)).perform(click())
        onView(withText("TV SHOWS")).perform(click())
        onView(withId(R.id.rv_fav_tv)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_fav_tv)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.image_poster)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_tagline)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_duration)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_description)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_genre)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_rating)).check(matches(isDisplayed()))
        onView(withId(R.id.scrollView)).perform(ViewActions.swipeUp())
        onView(withId(R.id.tv_status)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_language)).check(matches(isDisplayed()))
    }

    @Test
    fun pLoadSnackBarFavoriteTvShow() {
        onView(withId(R.id.favoriteFragment)).perform(click())
        onView(withText("TV SHOWS")).perform(click())
        onView(withId(R.id.rv_fav_tv)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_fav_tv)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.tv_genre)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_genre)).perform(click())
        onView(withId(com.google.android.material.R.id.snackbar_text)).check(matches(isDisplayed()))
    }


    @Test
    fun qRemoveFavoriteMovie() {
        onView(withId(R.id.favoriteFragment)).perform(click())
        onView(withId(R.id.rv_fav_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_fav_movie)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.content_detail)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_add_favorite)).perform(click())
        pressBack()
        onView(withId(R.id.iv_no_movie)).check(matches(isDisplayed()))
    }

    @Test
    fun rRemoveFavoriteTvShow() {
        onView(withId(R.id.favoriteFragment)).perform(click())
        onView(withText("TV SHOWS")).perform(click())
        onView(withId(R.id.rv_fav_tv)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_fav_tv)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.content_detail)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_add_favorite)).perform(click())
        pressBack()
        onView(withId(R.id.iv_no_tv_show)).check(matches(isDisplayed()))
    }

    @Test
    fun sLoadAboutFragment() {
        onView(withId(R.id.aboutFragment)).perform(click())
        onView(withId(R.id.text_top)).check(matches(isDisplayed()))
        onView(withId(R.id.image_app)).check(matches(isDisplayed()))
        onView(withId(R.id.text_about)).check(matches(isDisplayed()))
    }
}