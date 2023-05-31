from django.db import models
from django.contrib.auth.models import User
from django.shortcuts import render, redirect
    
class CourseChoice(models.Model):
    user = models.ForeignKey(User, on_delete=models.CASCADE)
    course_name = models.CharField(max_length=255)
    credits = models.IntegerField()
    ma_level = models.CharField(max_length=10)

    from django.shortcuts import render, redirect

def store_selected_courses(request):
    if request.method == 'POST':
        selected_courses = []

        # Retrieve the selected courses from the POST data
        for key, value in request.POST.items():
            if key.startswith('course_') and value == 'on':
                course_id = key.replace('course_', '')
                selected_courses.append(course_id)

        # Store the selected courses in session storage
        request.session['selected_courses'] = selected_courses

        # Redirect to the next page
        return redirect('semester_distribution') 

    # If the request method is not POST or the form was not submitted, render the template with the form
    return render(request, 'your_template.html')

