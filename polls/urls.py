from django.urls import path
from .views import CustomLoginView, CustomLogoutView, LandingView, results, masters, mt, register,download_all_choices, robotics, neuroX, datascience, semesters, PDF

app_name = 'polls'

urlpatterns = [
    path("login/", CustomLoginView.as_view(), name="login"),
    path('register/', register, name='register'),
    path("logout/", CustomLogoutView.as_view(), name="logout"),
    path("", LandingView.as_view(), name="landing"),
    path('results/', results, name='results'),
    path("download/all/", download_all_choices, name="download_all_choices"),
    path('robotics/', robotics, name='robotics'),
    path('neuroX/', neuroX, name='neuroX'),
    path('mt/', mt, name='mt'),
    path('datascience/', datascience, name='datascience'),
    path('semesters/', semesters, name='semesters'),
    path('masters/', masters, name='masters'), 
    path('PDF/', PDF, name='PDF'), 
]
