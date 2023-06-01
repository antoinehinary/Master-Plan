from django.http import HttpResponse, HttpResponseRedirect
from django.shortcuts import get_object_or_404, render, redirect
from django.views import generic
from django.utils import timezone
from openpyxl import Workbook
from django.contrib.auth.decorators import login_required
from django.contrib.auth.views import LoginView, LogoutView
from django.contrib.auth.models import User
from django.contrib import messages
from django.contrib.auth.models import User
from django.contrib.auth import login
from django.shortcuts import render



class LandingView(generic.TemplateView):
    template_name = "polls/landing.html"


class CustomLoginView(LoginView):
    template_name = 'registration/login.html'  # Set the template name for login

    def form_valid(self, form):
        messages.success(self.request, 'Login successful.')
        return redirect('polls:masters')
    
class CustomLogoutView(LogoutView):
    # Add any additional logic or customization if needed
    pass


def export_all_choices_to_excel():
    # Create a new workbook
    workbook = Workbook()
    sheet = workbook.active

    # Retrieve all questions and their choices
    questions = Question.objects.all()
    for question in questions:
        choices = question.choice_set.all()
        for choice in choices:
            # Write the choice data to the Excel file
            sheet.append([question.question_text, choice.choice_text])

    # Set the desired file path
    file_path = "desktop"
    # Save the workbook
    workbook.save(file_path)

    return file_path

def download_all_choices(request):
    # Generate the Excel file and save it
    file_path = export_all_choices_to_excel()  # Implement this function to generate the Excel file

    # Serve the file for download
    with open(file_path, "rb") as excel_file:
        response = HttpResponse(excel_file, content_type="application/vnd.ms-excel")
        response["Content-Disposition"] = 'attachment; filename="all_choices.xlsx"'
        return response

def robotics(request):
    return render(request, 'polls/robotics.html')

def neuroX(request):
    return render(request, 'polls/neuroX.html')

def datascience(request):
    return render(request, 'polls/datascience.html')

def mt(request):
    return render(request, 'polls/mt.html')

def semesters(request):
    return render(request, 'polls/semesters.html')

def masters(request):
    return render(request, 'polls/masters.html')

def results(request):
    return render(request, 'polls/results.html')

def PDF(request):
    return render(request, 'polls/PDF.html')

def register(request):
    if request.method == 'POST':
        username = request.POST['username']
        email = request.POST['email']
        password1 = request.POST['password1']
        password2 = request.POST['password2']

        # Create the user
        user = User.objects.create_user(username=username, email=email, password=password1)
        user.save()

        # Log in the user
        login(request, user)

        # Set the user ID in the session
        request.session['user_id'] = str(user.id)

        messages.success(request, "Registration successful. You are now logged in.")
        return redirect('polls:landing')  # Redirect to the home page

    return render(request, 'registration/register.html')


@login_required
def landing(request):
    # Add your landing page logic here
    return render(request, "polls/masters.html")
