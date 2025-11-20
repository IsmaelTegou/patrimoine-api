import React, { createContext, useContext, useState, useEffect } from 'react'
import { useNavigate } from 'react-router-dom'

const AuthContext = createContext(null)

export const useAuth = () => useContext(AuthContext)

// Simple client-side auth using localStorage (demo only)
export const AuthProvider = ({ children }) => {
  const navigate = useNavigate()
  const [user, setUser] = useState(() => {
    try {
      const raw = localStorage.getItem('auth_user')
      return raw ? JSON.parse(raw) : null
    } catch (e) {
      return null
    }
  })

  useEffect(() => {
    if (user) {
      localStorage.setItem('auth_user', JSON.stringify(user))
    } else {
      localStorage.removeItem('auth_user')
    }
  }, [user])

  const signUp = async ({ name, email, password, phone }) => {
    // Keep a simple users store in localStorage
    const users = JSON.parse(localStorage.getItem('users') || '[]')
    if (users.find((u) => u.email === email)) {
      throw new Error('Un compte existe déjà avec cet email')
    }
  const newUser = { id: Date.now(), name, email, password, phone }
    users.push(newUser)
    localStorage.setItem('users', JSON.stringify(users))
    // Do not auto-login after sign up (redirect user to sign-in)
    return newUser
  }

  const signIn = async ({ email, password }) => {
    // Check for default admin credentials
    if (email === 'admin@gmail.com' && password === 'admin') {
      const adminUser = { id: 0, name: 'Admin', email: 'admin@gmail.com', role: 'admin' };
      setUser(adminUser);
      return adminUser;
    }

    // Check for default user credentials
    if (email === 'aoudou@gmail.com' && password === '123456') {
      const defaultUser = { id: 1, name: 'Aoudou', email: 'aoudou@gmail.com', role: 'user' };
      setUser(defaultUser);
      return defaultUser;
    }

    // Otherwise check localStorage users
    const users = JSON.parse(localStorage.getItem('users') || '[]')
    const found = users.find((u) => u.email === email && u.password === password)
    if (!found) {
      throw new Error("Email ou mot de passe invalide")
    }
    setUser({ id: found.id, name: found.name, email: found.email, role: 'user' })
    return found
  }

  const signOut = () => {
    setUser(null)
    navigate('/signin')
  }

  return (
    <AuthContext.Provider value={{ user, signUp, signIn, signOut }}>
      {children}
    </AuthContext.Provider>
  )
}
