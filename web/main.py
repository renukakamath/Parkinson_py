from flask import *
from public import public
from admin import admin
from doctor import doctor
from pharmacy import pharmacy
from api import api


app=Flask(__name__)
app.secret_key="hello"
app.register_blueprint(public)
app.register_blueprint(admin,url_prefix='/admin')
app.register_blueprint(doctor,url_prefix='/doctor')
app.register_blueprint(pharmacy,url_prefix='/pharmacy')
app.register_blueprint(api,url_prefix='/api')


app.run(debug=True,port=5123,host="0.0.0.0")



